package com.anysoftkeyboard.janus.app.viewmodels

import app.cash.turbine.test
import com.anysoftkeyboard.janus.app.repository.FakeTranslationRepository
import com.anysoftkeyboard.janus.app.repository.OptionalSourceTerm
import com.anysoftkeyboard.janus.app.util.FakeStringProvider
import com.anysoftkeyboard.janus.app.util.TranslationFlowMessages
import com.anysoftkeyboard.janus.database.entities.Translation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class TranslateViewModelTest {

  private lateinit var viewModel: TranslateViewModel
  private lateinit var fakeRepository: FakeTranslationRepository
  private lateinit var mockWelcomeMessageProvider:
      com.anysoftkeyboard.janus.app.util.TranslationFlowMessagesProvider
  private lateinit var mockRecentLanguagesRepository:
      com.anysoftkeyboard.janus.app.repository.RecentLanguagesRepository
  private lateinit var mockLanguageDetector: com.anysoftkeyboard.janus.app.util.LanguageDetector

  private val testDispatcher = StandardTestDispatcher()

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    fakeRepository = FakeTranslationRepository(mock(), mock(), FakeStringProvider())
    mockWelcomeMessageProvider = mock()
    mockRecentLanguagesRepository = mock()
    mockLanguageDetector = mock()
    whenever(mockRecentLanguagesRepository.recentLanguages)
        .thenReturn(kotlinx.coroutines.flow.MutableStateFlow(emptyList()))
    whenever(mockRecentLanguagesRepository.currentSourceLanguage)
        .thenReturn(kotlinx.coroutines.flow.MutableStateFlow("en"))
    whenever(mockRecentLanguagesRepository.currentTargetLanguage)
        .thenReturn(kotlinx.coroutines.flow.MutableStateFlow("he"))

    whenever(mockWelcomeMessageProvider.getRandomMessage())
        .thenReturn(
            TranslationFlowMessages(
                com.anysoftkeyboard.janus.app.R.string.empty_state_initial,
                com.anysoftkeyboard.janus.app.R.string.loading_state_initial,
                com.anysoftkeyboard.janus.app.R.string.loading_state_detecting,
                com.anysoftkeyboard.janus.app.R.string.search_instruction_initial,
            )
        )
    viewModel =
        TranslateViewModel(
            fakeRepository,
            mockRecentLanguagesRepository,
            FakeStringProvider(),
            mockWelcomeMessageProvider,
            mockLanguageDetector,
        )
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `initial state is empty`() = runTest {
    assertEquals(TranslateViewState.Empty, viewModel.pageState.value)
  }

  @Test
  fun `initial welcome message is set from provider`() = runTest {
    val message = viewModel.welcomeMessage.value
    assertEquals(
        com.anysoftkeyboard.janus.app.R.string.empty_state_initial,
        message.welcomeMessageResId,
    )
    assertEquals(
        com.anysoftkeyboard.janus.app.R.string.loading_state_detecting,
        message.detectingMessageResId,
    )
    assertEquals(
        com.anysoftkeyboard.janus.app.R.string.search_instruction_initial,
        message.searchInstructionResId,
    )
  }

  @Test
  fun `searchArticles success returns options with available languages`() = runTest {
    val searchResults =
        listOf(
            OptionalSourceTerm(
                pageid = 1,
                title = "Summer",
                snippet = "hottest season",
                availableLanguages = listOf("he", "fr", "de"),
            ),
            OptionalSourceTerm(
                pageid = 2,
                title = "Winter",
                snippet = "coldest season",
                availableLanguages = listOf("he", "es"),
            ),
        )
    fakeRepository.nextSearchResults = searchResults

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles("en", "season")
      assertEquals(TranslateViewState.FetchingOptions, awaitItem())
      advanceUntilIdle()

      val state = awaitItem()
      assertTrue(state is TranslateViewState.OptionsFetched)
      val optionsFetched = state as TranslateViewState.OptionsFetched
      assertEquals(2, optionsFetched.options.size)
      assertEquals("Summer", optionsFetched.options[0].title)
      assertEquals(listOf("he", "fr", "de"), optionsFetched.options[0].availableLanguages)
      assertEquals("Winter", optionsFetched.options[1].title)
      assertEquals(listOf("he", "es"), optionsFetched.options[1].availableLanguages)
      assertEquals("en", optionsFetched.effectiveSourceLang)
    }
  }

  @Test
  fun `searchArticles with auto source language uses detected language`() = runTest {
    val term = "hola"
    val detectedLang = "es"
    whenever(mockLanguageDetector.detect(term))
        .thenReturn(com.anysoftkeyboard.janus.app.util.DetectionResult.Success(detectedLang, 0.9f))

    val searchResults =
        listOf(
            OptionalSourceTerm(
                pageid = 1,
                title = "Hola",
                snippet = "Spanish greeting",
                availableLanguages = listOf("en"),
            )
        )
    fakeRepository.nextSearchResults = searchResults

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles(
          com.anysoftkeyboard.janus.app.util.LanguageDetector.AUTO_DETECT_LANGUAGE_CODE,
          term,
      )
      assertEquals(TranslateViewState.FetchingOptions, awaitItem())
      assertEquals(TranslateViewState.Detecting, awaitItem())
      advanceUntilIdle()

      val state = awaitItem()
      assertTrue(state is TranslateViewState.OptionsFetched)
      val optionsFetched = state as TranslateViewState.OptionsFetched
      // Verify repository was called with detected language "es"
      // Verify by checking the results which are returned by the fake repo (it doesn't validate
      // args but we know flow continued)
      // To strictly verify arg, we'd need a spy on repository, but checking flow completion is
      // good enough for now.
      // We can also verify the detector was called.
      org.mockito.kotlin.verify(mockLanguageDetector).detect(term)
      assertEquals("es", optionsFetched.effectiveSourceLang)
    }
  }

  @Test
  fun `searchArticles with no available languages`() = runTest {
    val searchResults =
        listOf(
            OptionalSourceTerm(
                pageid = 1,
                title = "Rare Article",
                snippet = "only in English",
                availableLanguages = emptyList(),
            )
        )
    fakeRepository.nextSearchResults = searchResults

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles("en", "rare")
      assertEquals(TranslateViewState.FetchingOptions, awaitItem())
      advanceUntilIdle()

      val state = awaitItem()
      assertTrue(state is TranslateViewState.OptionsFetched)
      val optionsFetched = state as TranslateViewState.OptionsFetched
      assertEquals(1, optionsFetched.options.size)
      assertEquals(emptyList<String>(), optionsFetched.options[0].availableLanguages)
    }
  }

  @Test
  fun `searchArticles with empty source language does nothing`() = runTest {
    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles("", "term")

      // Should not emit FetchingOptions or any other state
      expectNoEvents()
    }
  }

  @Test
  fun `fetchTranslation success with target language available`() = runTest {
    val searchTerm =
        OptionalSourceTerm(
            pageid = 1,
            title = "Summer",
            snippet = "hottest season",
            availableLanguages = listOf("he"),
        )
    val translation =
        Translation(
            sourceWord = "Summer",
            sourceLangCode = "en",
            sourceArticleUrl = "url",
            sourceShortDescription = "desc",
            sourceSummary = "summary",
            translatedWord = "קיץ",
            targetLangCode = "he",
            targetArticleUrl = "url_he",
            targetShortDescription = "desc_he",
            targetSummary = "summary_he",
        )
    fakeRepository.nextTranslations = listOf(translation)

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      val optionsFetched =
          TranslateViewState.OptionsFetched("test", listOf(searchTerm), emptyMap(), "en")
      viewModel.fetchTranslation(optionsFetched, searchTerm, "he")

      // First update: Translating state
      val translatingState = awaitItem()
      assertTrue(translatingState is TranslateViewState.Translating)

      advanceUntilIdle()

      // Second update: Translated state
      val translatedState = awaitItem()
      assertTrue(translatedState is TranslateViewState.Translated)
      val translated = translatedState as TranslateViewState.Translated
      assertEquals("Summer", translated.term.title)
      assertEquals("en", translated.sourceLang)
      assertEquals("he", translated.targetLang)
      assertTrue(translated.translation is TranslationState.Translated)
      val translationState = translated.translation as TranslationState.Translated
      assertEquals("קיץ", translationState.translation.translatedWord)
    }
  }

  @Test
  fun `fetchTranslation when target language not available`() = runTest {
    val searchTerm =
        OptionalSourceTerm(
            pageid = 1,
            title = "Summer",
            snippet = "hottest season",
            availableLanguages = listOf("fr", "de"),
        )
    val availableTranslations =
        listOf(
            Translation(
                sourceWord = "Summer",
                sourceLangCode = "en",
                sourceArticleUrl = "url",
                sourceShortDescription = "desc",
                sourceSummary = "summary",
                translatedWord = "Été",
                targetLangCode = "fr",
                targetArticleUrl = "url_fr",
                targetShortDescription = null,
                targetSummary = null,
            ),
            Translation(
                sourceWord = "Summer",
                sourceLangCode = "en",
                sourceArticleUrl = "url",
                sourceShortDescription = "desc",
                sourceSummary = "summary",
                translatedWord = "Sommer",
                targetLangCode = "de",
                targetArticleUrl = "url_de",
                targetShortDescription = null,
                targetSummary = null,
            ),
        )
    fakeRepository.nextTranslations = availableTranslations

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      val optionsFetched =
          TranslateViewState.OptionsFetched("test", listOf(searchTerm), emptyMap(), "en")
      viewModel.fetchTranslation(optionsFetched, searchTerm, "he")

      // First update: Translating state
      val translatingState = awaitItem()
      assertTrue(translatingState is TranslateViewState.Translating)

      advanceUntilIdle()

      // Second update: MissingTranslation state
      val translatedState = awaitItem()
      assertTrue(translatedState is TranslateViewState.Translated)
      val translated = translatedState as TranslateViewState.Translated
      assertTrue(translated.translation is TranslationState.MissingTranslation)
      val missingTranslation = translated.translation as TranslationState.MissingTranslation
      assertEquals("he", missingTranslation.missingLang)
      assertEquals(2, missingTranslation.availableTranslations.size)
      assertEquals("fr", missingTranslation.availableTranslations[0].targetLangCode)
      assertEquals("de", missingTranslation.availableTranslations[1].targetLangCode)
    }
  }

  @Test
  fun `searchArticles error sets error state with details`() = runTest {
    val testException = IllegalStateException("Network error")
    fakeRepository.searchException = testException

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles("en", "test")
      assertEquals(TranslateViewState.FetchingOptions, awaitItem())
      advanceUntilIdle()

      val errorState = awaitItem()
      assertTrue(errorState is TranslateViewState.Error)
      val error = errorState as TranslateViewState.Error
      assertEquals(TranslateViewModel.ErrorType.Unknown, error.errorType)
      assertEquals("Network error", error.errorMessage)
    }
  }

  @Test
  fun `searchArticles maps UnknownHostException to Network error`() = runTest {
    fakeRepository.searchException = java.net.UnknownHostException()

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles("en", "test")
      skipItems(1) // FetchingOptions
      advanceUntilIdle()

      val errorState = awaitItem()
      assertTrue(errorState is TranslateViewState.Error)
      assertEquals(
          TranslateViewModel.ErrorType.Network,
          (errorState as TranslateViewState.Error).errorType,
      )
    }
  }

  @Test
  fun `searchArticles maps HttpException 429 to RateLimit error`() = runTest {
    val response = retrofit2.Response.error<Any>(429, okhttp3.ResponseBody.create(null, ""))
    fakeRepository.searchException = retrofit2.HttpException(response)

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles("en", "test")
      skipItems(1) // FetchingOptions
      advanceUntilIdle()

      val errorState = awaitItem()
      assertTrue(errorState is TranslateViewState.Error)
      assertEquals(
          TranslateViewModel.ErrorType.RateLimit,
          (errorState as TranslateViewState.Error).errorType,
      )
    }
  }

  @Test
  fun `searchArticles maps HttpException 404 to NotFound error`() = runTest {
    val response = retrofit2.Response.error<Any>(404, okhttp3.ResponseBody.create(null, ""))
    fakeRepository.searchException = retrofit2.HttpException(response)

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles("en", "test")
      skipItems(1) // FetchingOptions
      advanceUntilIdle()

      val errorState = awaitItem()
      assertTrue(errorState is TranslateViewState.Error)
      assertEquals(
          TranslateViewModel.ErrorType.NotFound,
          (errorState as TranslateViewState.Error).errorType,
      )
    }
  }

  @Test
  fun `searchArticles maps HttpException 500 to Server error`() = runTest {
    val response = retrofit2.Response.error<Any>(500, okhttp3.ResponseBody.create(null, ""))
    fakeRepository.searchException = retrofit2.HttpException(response)

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles("en", "test")
      skipItems(1) // FetchingOptions
      advanceUntilIdle()

      val errorState = awaitItem()
      assertTrue(errorState is TranslateViewState.Error)
      assertEquals(
          TranslateViewModel.ErrorType.Server,
          (errorState as TranslateViewState.Error).errorType,
      )
    }
  }

  @Test
  fun `fetchTranslation error stores error in translations map`() = runTest {
    val searchTerm =
        OptionalSourceTerm(
            pageid = 1,
            title = "Summer",
            snippet = "hottest season",
            availableLanguages = listOf("he"),
        )
    val testException = RuntimeException("Failed to fetch translation")
    fakeRepository.fetchException = testException

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      val optionsFetched =
          TranslateViewState.OptionsFetched("test", listOf(searchTerm), emptyMap(), "en")
      viewModel.fetchTranslation(optionsFetched, searchTerm, "he")

      // First update: Translating state
      val translatingState = awaitItem()
      assertTrue(translatingState is TranslateViewState.Translating)

      advanceUntilIdle()

      // Second update: Error state
      val errorState = awaitItem()
      assertTrue(errorState is TranslateViewState.Error)
      val error = errorState as TranslateViewState.Error
      assertEquals("Failed to fetch translation", error.errorMessage)
      assertEquals(TranslateViewModel.ErrorType.Unknown, error.errorType)
    }
  }

  @Test
  fun `backToSearchResults navigates from Translated to OptionsFetched state`() = runTest {
    val searchTerm =
        OptionalSourceTerm(
            pageid = 1,
            title = "Summer",
            snippet = "hottest season",
            availableLanguages = listOf("he"),
        )
    val translation =
        Translation(
            sourceWord = "Summer",
            sourceLangCode = "en",
            sourceArticleUrl = "url",
            sourceShortDescription = "desc",
            sourceSummary = "summary",
            translatedWord = "קיץ",
            targetLangCode = "he",
            targetArticleUrl = "url_he",
            targetShortDescription = "desc_he",
            targetSummary = "summary_he",
        )
    fakeRepository.nextTranslations = listOf(translation)

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      val optionsFetched =
          TranslateViewState.OptionsFetched("test", listOf(searchTerm), emptyMap(), "en")
      viewModel.fetchTranslation(optionsFetched, searchTerm, "he")

      // Skip to translated state
      skipItems(1) // Translating state
      advanceUntilIdle()
      val translatedState = awaitItem()
      assertTrue(translatedState is TranslateViewState.Translated)

      // Navigate back to search results
      viewModel.backToSearchResults()
      val backState = awaitItem()
      assertTrue(backState is TranslateViewState.OptionsFetched)
      val backToOptions = backState as TranslateViewState.OptionsFetched
      assertEquals("test", backToOptions.searchTerm)
      assertEquals(1, backToOptions.options.size)
    }
  }

  @Test
  fun `backToSearchResults with no previous results returns to Empty state`() = runTest {
    // Initial state is Empty
    assertEquals(TranslateViewState.Empty, viewModel.pageState.value)

    // Call backToSearchResults without any previous search
    viewModel.backToSearchResults()

    // State should remain Empty (StateFlow doesn't emit if value doesn't change)
    assertEquals(TranslateViewState.Empty, viewModel.pageState.value)
  }

  @Test
  fun `clearSearch navigates from OptionsFetched to Empty state`() = runTest {
    val searchResults =
        listOf(
            OptionalSourceTerm(
                pageid = 1,
                title = "Summer",
                snippet = "hottest season",
                availableLanguages = listOf("he"),
            )
        )
    fakeRepository.nextSearchResults = searchResults

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles("en", "season")
      skipItems(1) // FetchingOptions state
      advanceUntilIdle()
      val optionsFetched = awaitItem()
      assertTrue(optionsFetched is TranslateViewState.OptionsFetched)

      // Clear search
      viewModel.clearSearch()
      val clearedState = awaitItem()
      assertTrue(clearedState is TranslateViewState.Empty)
    }
  }

  @Test
  fun `clearSearch navigates from Error to Empty state`() = runTest {
    val testException = IllegalStateException("Network error")
    fakeRepository.searchException = testException

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles("en", "test")
      skipItems(1) // FetchingOptions state
      advanceUntilIdle()
      val errorState = awaitItem()
      assertTrue(errorState is TranslateViewState.Error)

      // Clear search from error state
      viewModel.clearSearch()
      val clearedState = awaitItem()
      assertTrue(clearedState is TranslateViewState.Empty)
    }
  }

  @Test
  fun `clearSearch also clears saved search results`() = runTest {
    val searchTerm =
        OptionalSourceTerm(
            pageid = 1,
            title = "Summer",
            snippet = "hottest season",
            availableLanguages = listOf("he"),
        )
    val translation =
        Translation(
            sourceWord = "Summer",
            sourceLangCode = "en",
            sourceArticleUrl = "url",
            sourceShortDescription = "desc",
            sourceSummary = "summary",
            translatedWord = "קיץ",
            targetLangCode = "he",
            targetArticleUrl = "url_he",
            targetShortDescription = "desc_he",
            targetSummary = "summary_he",
        )
    fakeRepository.nextTranslations = listOf(translation)

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      val optionsFetched =
          TranslateViewState.OptionsFetched("test", listOf(searchTerm), emptyMap(), "en")
      viewModel.fetchTranslation(optionsFetched, searchTerm, "he")

      skipItems(1) // Translating state
      advanceUntilIdle()
      skipItems(1) // Translated state

      // Clear search (which clears saved results)
      viewModel.clearSearch()
      val clearedState = awaitItem()
      assertTrue(clearedState is TranslateViewState.Empty)

      // Try to go back - should stay Empty since saved results were cleared
      // No need to await since Empty -> Empty doesn't emit
      viewModel.backToSearchResults()
      assertEquals(TranslateViewState.Empty, viewModel.pageState.value)
    }
  }

  @Test
  fun `clearSearch updates welcome message`() = runTest {
    // Setup mock to return different messages
    val message1 =
        TranslationFlowMessages(
            com.anysoftkeyboard.janus.app.R.string.empty_state_initial,
            com.anysoftkeyboard.janus.app.R.string.loading_state_initial,
            com.anysoftkeyboard.janus.app.R.string.loading_state_detecting,
            com.anysoftkeyboard.janus.app.R.string.search_instruction_initial,
        )
    val message2 =
        TranslationFlowMessages(
            com.anysoftkeyboard.janus.app.R.string.empty_state_initial_1,
            com.anysoftkeyboard.janus.app.R.string.loading_state_initial_1,
            com.anysoftkeyboard.janus.app.R.string.loading_state_detecting_1,
            com.anysoftkeyboard.janus.app.R.string.search_instruction_initial_1,
        )

    whenever(mockWelcomeMessageProvider.getRandomMessage())
        .thenReturn(message1)
        .thenReturn(message2)

    // Re-initialize view model to trigger first call
    viewModel =
        TranslateViewModel(
            fakeRepository,
            mockRecentLanguagesRepository,
            FakeStringProvider(),
            mockWelcomeMessageProvider,
            mockLanguageDetector,
        )

    viewModel.welcomeMessage.test {
      assertEquals(message1, awaitItem())

      viewModel.clearSearch()
      assertEquals(message2, awaitItem())
    }
  }

  @Test
  fun `setSourceLanguage calls repository`() = runTest {
    viewModel.setSourceLanguage("fr")
    org.mockito.kotlin.verify(mockRecentLanguagesRepository).setSourceLanguage("fr")
  }

  @Test
  fun `setTargetLanguage calls repository`() = runTest {
    viewModel.setTargetLanguage("de")
    org.mockito.kotlin.verify(mockRecentLanguagesRepository).setTargetLanguage("de")
  }

  @Test
  fun `updateRecentLanguage calls repository`() = runTest {
    viewModel.updateRecentLanguage("es")
    org.mockito.kotlin.verify(mockRecentLanguagesRepository).addRecentLanguage("es")
  }

  @Test
  fun `searchArticles with ambiguous result prompts disambiguation`() = runTest {
    val term = "queso"
    val candidates =
        listOf(
            com.anysoftkeyboard.janus.app.util.DetectionResult.Ambiguous.Candidate("es", 0.9f),
            com.anysoftkeyboard.janus.app.util.DetectionResult.Ambiguous.Candidate("pt", 0.8f),
        )
    whenever(mockLanguageDetector.detect(term))
        .thenReturn(com.anysoftkeyboard.janus.app.util.DetectionResult.Ambiguous(candidates))

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles(
          com.anysoftkeyboard.janus.app.util.LanguageDetector.AUTO_DETECT_LANGUAGE_CODE,
          term,
      )
      assertEquals(TranslateViewState.FetchingOptions, awaitItem())
      assertEquals(TranslateViewState.Detecting, awaitItem())
      advanceUntilIdle()

      val state = awaitItem()
      assertTrue(state is TranslateViewState.AmbiguousSource)
      val ambiguousState = state as TranslateViewState.AmbiguousSource
      assertEquals(listOf("es", "pt"), ambiguousState.candidates)
      assertEquals(term, ambiguousState.originalQuery)

      // Now resolve
      val searchResults = listOf(OptionalSourceTerm(1, "Queso", "Cheese", listOf("en")))
      fakeRepository.nextSearchResults = searchResults
      viewModel.resolveAmbiguity("es", term)
      assertEquals(TranslateViewState.FetchingOptions, awaitItem())
      val finalState = awaitItem()
      assertTrue(finalState is TranslateViewState.OptionsFetched)
      assertEquals("es", (finalState as TranslateViewState.OptionsFetched).effectiveSourceLang)
    }
  }

  @Test
  fun `searchArticles with safety violation sets error state`() = runTest {
    val term = "unsafe phrase"
    whenever(mockLanguageDetector.detect(term))
        .thenReturn(com.anysoftkeyboard.janus.app.util.DetectionResult.SafetyViolation)

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      viewModel.searchArticles(
          com.anysoftkeyboard.janus.app.util.LanguageDetector.AUTO_DETECT_LANGUAGE_CODE,
          term,
      )
      assertEquals(TranslateViewState.FetchingOptions, awaitItem())
      assertEquals(TranslateViewState.Detecting, awaitItem())
      advanceUntilIdle()

      val state = awaitItem()
      assertTrue(state is TranslateViewState.Error)
      val error = state as TranslateViewState.Error
      assertEquals(TranslateViewModel.ErrorType.SafetyViolation, error.errorType)
    }
  }
}
