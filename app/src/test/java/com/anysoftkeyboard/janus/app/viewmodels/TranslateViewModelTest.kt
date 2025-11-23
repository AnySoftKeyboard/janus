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
      com.anysoftkeyboard.janus.app.util.WelcomeMessageProvider

  private val testDispatcher = StandardTestDispatcher()

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    fakeRepository = FakeTranslationRepository(mock(), mock(), FakeStringProvider())
    mockWelcomeMessageProvider = mock()
    whenever(mockWelcomeMessageProvider.getRandomMessage())
        .thenReturn(
            TranslationFlowMessages(
                com.anysoftkeyboard.janus.app.R.string.empty_state_initial,
                com.anysoftkeyboard.janus.app.R.string.search_instruction_initial))
    viewModel = TranslateViewModel(fakeRepository, FakeStringProvider(), mockWelcomeMessageProvider)
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
        com.anysoftkeyboard.janus.app.R.string.empty_state_initial, message.welcomeMessageResId)
    assertEquals(
        com.anysoftkeyboard.janus.app.R.string.search_instruction_initial,
        message.searchInstructionResId)
  }

  @Test
  fun `searchArticles success returns options with available languages`() = runTest {
    val searchResults =
        listOf(
            OptionalSourceTerm(
                pageid = 1,
                title = "Summer",
                snippet = "hottest season",
                availableLanguages = listOf("he", "fr", "de")),
            OptionalSourceTerm(
                pageid = 2,
                title = "Winter",
                snippet = "coldest season",
                availableLanguages = listOf("he", "es")))
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
                availableLanguages = emptyList()))
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
  fun `fetchTranslation success with target language available`() = runTest {
    val searchTerm =
        OptionalSourceTerm(
            pageid = 1,
            title = "Summer",
            snippet = "hottest season",
            availableLanguages = listOf("he"))
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
            targetSummary = "summary_he")
    fakeRepository.nextTranslations = listOf(translation)

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      val optionsFetched = TranslateViewState.OptionsFetched("test", listOf(searchTerm), emptyMap())
      viewModel.fetchTranslation(optionsFetched, searchTerm, "en", "he")

      // First update: Translating state
      val translatingState = awaitItem()
      assertTrue(translatingState is TranslateViewState.OptionsFetched)
      val translating = translatingState as TranslateViewState.OptionsFetched
      assertTrue(translating.translations[searchTerm] is TranslationState.Translating)

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
            availableLanguages = listOf("fr", "de"))
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
                targetSummary = null),
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
                targetSummary = null))
    fakeRepository.nextTranslations = availableTranslations

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      val optionsFetched = TranslateViewState.OptionsFetched("test", listOf(searchTerm), emptyMap())
      viewModel.fetchTranslation(optionsFetched, searchTerm, "en", "he")

      // First update: Translating state
      val translatingState = awaitItem()
      assertTrue(translatingState is TranslateViewState.OptionsFetched)

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
      assertEquals("IllegalStateException", error.errorType)
      assertEquals("Network error", error.errorMessage)
    }
  }

  @Test
  fun `fetchTranslation error stores error in translations map`() = runTest {
    val searchTerm =
        OptionalSourceTerm(
            pageid = 1,
            title = "Summer",
            snippet = "hottest season",
            availableLanguages = listOf("he"))
    val testException = RuntimeException("Failed to fetch translation")
    fakeRepository.fetchException = testException

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      val optionsFetched = TranslateViewState.OptionsFetched("test", listOf(searchTerm), emptyMap())
      viewModel.fetchTranslation(optionsFetched, searchTerm, "en", "he")

      // First update: Translating state
      val translatingState = awaitItem()
      assertTrue(translatingState is TranslateViewState.OptionsFetched)
      val translating = translatingState as TranslateViewState.OptionsFetched
      assertTrue(translating.translations[searchTerm] is TranslationState.Translating)

      advanceUntilIdle()

      // Second update: Error state in translations map
      val errorState = awaitItem()
      assertTrue(errorState is TranslateViewState.OptionsFetched)
      val withError = errorState as TranslateViewState.OptionsFetched
      val errorTranslation = withError.translations[searchTerm]
      assertTrue(errorTranslation is TranslationState.Error)
      val error = errorTranslation as TranslationState.Error
      assertEquals("Failed to fetch translation", error.errorMessage)
    }
  }

  @Test
  fun `backToSearchResults navigates from Translated to OptionsFetched state`() = runTest {
    val searchTerm =
        OptionalSourceTerm(
            pageid = 1,
            title = "Summer",
            snippet = "hottest season",
            availableLanguages = listOf("he"))
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
            targetSummary = "summary_he")
    fakeRepository.nextTranslations = listOf(translation)

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      val optionsFetched = TranslateViewState.OptionsFetched("test", listOf(searchTerm), emptyMap())
      viewModel.fetchTranslation(optionsFetched, searchTerm, "en", "he")

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
                availableLanguages = listOf("he")))
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
            availableLanguages = listOf("he"))
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
            targetSummary = "summary_he")
    fakeRepository.nextTranslations = listOf(translation)

    viewModel.pageState.test {
      assertEquals(TranslateViewState.Empty, awaitItem())

      val optionsFetched = TranslateViewState.OptionsFetched("test", listOf(searchTerm), emptyMap())
      viewModel.fetchTranslation(optionsFetched, searchTerm, "en", "he")

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
            com.anysoftkeyboard.janus.app.R.string.search_instruction_initial)
    val message2 =
        TranslationFlowMessages(
            com.anysoftkeyboard.janus.app.R.string.empty_state_initial_1,
            com.anysoftkeyboard.janus.app.R.string.search_instruction_initial_1)

    whenever(mockWelcomeMessageProvider.getRandomMessage())
        .thenReturn(message1)
        .thenReturn(message2)

    // Re-initialize view model to trigger first call
    viewModel = TranslateViewModel(fakeRepository, FakeStringProvider(), mockWelcomeMessageProvider)

    viewModel.welcomeMessage.test {
      assertEquals(message1, awaitItem())

      viewModel.clearSearch()
      assertEquals(message2, awaitItem())
    }
  }
}
