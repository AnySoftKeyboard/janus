package com.anysoftkeyboard.janus.app.viewmodels

import app.cash.turbine.test
import com.anysoftkeyboard.janus.app.repository.FakeTranslationRepository
import com.anysoftkeyboard.janus.app.util.FakeStringProvider
import com.anysoftkeyboard.janus.database.entities.Translation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class HistoryViewModelTest {

  private lateinit var viewModel: HistoryViewModel
  private lateinit var fakeRepository: FakeTranslationRepository

  private val testDispatcher = StandardTestDispatcher()

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    fakeRepository = FakeTranslationRepository(mock(), mock(), FakeStringProvider())
    viewModel = HistoryViewModel(fakeRepository)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `history is empty by default`() = runTest {
    assertEquals(emptyList<Translation>(), viewModel.history.value)
  }

  @Test
  fun `history updates when repository history changes`() = runTest {
    val testTranslations =
        listOf(
            Translation(
                sourceWord = "test1",
                sourceLangCode = "en",
                sourceArticleUrl = "url1",
                sourceShortDescription = "desc1",
                sourceSummary = "summary1",
                translatedWord = "test1_translated",
                targetLangCode = "es",
                targetArticleUrl = "url1_es",
                targetShortDescription = "desc1_es",
                targetSummary = "summary1_es"),
            Translation(
                sourceWord = "test2",
                sourceLangCode = "en",
                sourceArticleUrl = "url2",
                sourceShortDescription = "desc2",
                sourceSummary = "summary2",
                translatedWord = "test2_translated",
                targetLangCode = "es",
                targetArticleUrl = "url2_es",
                targetShortDescription = "desc2_es",
                targetSummary = "summary2_es"))

    viewModel.history.test {
      assertEquals(emptyList<Translation>(), awaitItem())

      fakeRepository.setHistory(testTranslations)

      val history = awaitItem()
      assertEquals(testTranslations.size, history.size)
      for (i in testTranslations.indices) {
        assertEquals(testTranslations[i].sourceWord, history[i].sourceWord)
        assertEquals(testTranslations[i].sourceLangCode, history[i].sourceLangCode)
        assertEquals(testTranslations[i].translatedWord, history[i].translatedWord)
        assertEquals(testTranslations[i].targetLangCode, history[i].targetLangCode)
        assertEquals(testTranslations[i].isFavorite, history[i].isFavorite)
      }
    }
  }

  @Test
  fun `searchQuery is empty by default`() = runTest {
    assertEquals("", viewModel.searchQuery.value)
  }

  @Test
  fun `updateSearchQuery updates search query state`() = runTest {
    viewModel.searchQuery.test {
      assertEquals("", awaitItem())

      viewModel.updateSearchQuery("cat")

      assertEquals("cat", awaitItem())
    }
  }

  @Test
  fun `clearSearch resets search query to empty`() = runTest {
    viewModel.updateSearchQuery("cat")
    assertEquals("cat", viewModel.searchQuery.value)

    viewModel.clearSearch()

    assertEquals("", viewModel.searchQuery.value)
  }

  @Test
  fun `history shows all items when search query is empty`() = runTest {
    val testTranslations =
        listOf(
            Translation(
                sourceWord = "Cat",
                sourceLangCode = "en",
                sourceArticleUrl = "url1",
                sourceShortDescription = "desc1",
                sourceSummary = "summary1",
                translatedWord = "Gato",
                targetLangCode = "es",
                targetArticleUrl = "url1_es",
                targetShortDescription = "desc1_es",
                targetSummary = "summary1_es"),
            Translation(
                sourceWord = "Dog",
                sourceLangCode = "en",
                sourceArticleUrl = "url2",
                sourceShortDescription = "desc2",
                sourceSummary = "summary2",
                translatedWord = "Perro",
                targetLangCode = "es",
                targetArticleUrl = "url2_es",
                targetShortDescription = "desc2_es",
                targetSummary = "summary2_es"))

    viewModel.history.test {
      // Skip initial empty item
      skipItems(1)

      fakeRepository.setHistory(testTranslations)
      testDispatcher.scheduler.advanceUntilIdle()

      val history = awaitItem()
      assertEquals(2, history.size)
    }
  }

  @Test
  fun `history filters by search query`() = runTest {
    val testTranslations =
        listOf(
            Translation(
                sourceWord = "Cat",
                sourceLangCode = "en",
                sourceArticleUrl = "url1",
                sourceShortDescription = "desc1",
                sourceSummary = "summary1",
                translatedWord = "Gato",
                targetLangCode = "es",
                targetArticleUrl = "url1_es",
                targetShortDescription = "desc1_es",
                targetSummary = "summary1_es"),
            Translation(
                sourceWord = "Dog",
                sourceLangCode = "en",
                sourceArticleUrl = "url2",
                sourceShortDescription = "desc2",
                sourceSummary = "summary2",
                translatedWord = "Perro",
                targetLangCode = "es",
                targetArticleUrl = "url2_es",
                targetShortDescription = "desc2_es",
                targetSummary = "summary2_es"))

    fakeRepository.setHistory(testTranslations)
    testDispatcher.scheduler.advanceUntilIdle()

    viewModel.history.test {
      // Skip initial items
      skipItems(1)

      viewModel.updateSearchQuery("Cat")
      testDispatcher.scheduler.advanceUntilIdle()

      val filteredHistory = awaitItem()
      assertEquals(1, filteredHistory.size)
      assertEquals("Cat", filteredHistory[0].sourceWord)
    }
  }

  @Test
  fun `history returns to full list when search is cleared`() = runTest {
    val testTranslations =
        listOf(
            Translation(
                sourceWord = "Cat",
                sourceLangCode = "en",
                sourceArticleUrl = "url1",
                sourceShortDescription = "desc1",
                sourceSummary = "summary1",
                translatedWord = "Gato",
                targetLangCode = "es",
                targetArticleUrl = "url1_es",
                targetShortDescription = "desc1_es",
                targetSummary = "summary1_es"),
            Translation(
                sourceWord = "Dog",
                sourceLangCode = "en",
                sourceArticleUrl = "url2",
                sourceShortDescription = "desc2",
                sourceSummary = "summary2",
                translatedWord = "Perro",
                targetLangCode = "es",
                targetArticleUrl = "url2_es",
                targetShortDescription = "desc2_es",
                targetSummary = "summary2_es"))

    viewModel.history.test {
      skipItems(1)

      fakeRepository.setHistory(testTranslations)
      testDispatcher.scheduler.advanceUntilIdle()
      val fullHistory = awaitItem()
      assertEquals(2, fullHistory.size)

      // Search for Cat
      viewModel.updateSearchQuery("Cat")
      testDispatcher.scheduler.advanceUntilIdle()
      val filteredHistory = awaitItem()
      assertEquals(1, filteredHistory.size)

      // Clear search
      viewModel.clearSearch()
      testDispatcher.scheduler.advanceUntilIdle()
      val restoredHistory = awaitItem()
      assertEquals(2, restoredHistory.size)
    }
  }

  @Test
  fun `history searches in translated word`() = runTest {
    val testTranslations =
        listOf(
            Translation(
                sourceWord = "Cat",
                sourceLangCode = "en",
                sourceArticleUrl = "url1",
                sourceShortDescription = "desc1",
                sourceSummary = "summary1",
                translatedWord = "Gato",
                targetLangCode = "es",
                targetArticleUrl = "url1_es",
                targetShortDescription = "desc1_es",
                targetSummary = "summary1_es"),
            Translation(
                sourceWord = "Dog",
                sourceLangCode = "en",
                sourceArticleUrl = "url2",
                sourceShortDescription = "desc2",
                sourceSummary = "summary2",
                translatedWord = "Perro",
                targetLangCode = "es",
                targetArticleUrl = "url2_es",
                targetShortDescription = "desc2_es",
                targetSummary = "summary2_es"))

    fakeRepository.setHistory(testTranslations)
    testDispatcher.scheduler.advanceUntilIdle()

    viewModel.history.test {
      skipItems(1)

      viewModel.updateSearchQuery("Gato")
      testDispatcher.scheduler.advanceUntilIdle()

      val filteredHistory = awaitItem()
      assertEquals(1, filteredHistory.size)
      assertEquals("Cat", filteredHistory[0].sourceWord)
    }
  }

  @Test
  fun `deleteTranslation removes item from history`() = runTest {
    val testTranslations =
        listOf(
            Translation(
                id = 1,
                sourceWord = "Cat",
                sourceLangCode = "en",
                sourceArticleUrl = "",
                sourceShortDescription = null,
                sourceSummary = null,
                translatedWord = "Gato",
                targetLangCode = "es",
                targetArticleUrl = "",
                targetShortDescription = null,
                targetSummary = null),
            Translation(
                id = 2,
                sourceWord = "Dog",
                sourceLangCode = "en",
                sourceArticleUrl = "",
                sourceShortDescription = null,
                sourceSummary = null,
                translatedWord = "Perro",
                targetLangCode = "es",
                targetArticleUrl = "",
                targetShortDescription = null,
                targetSummary = null))

    fakeRepository.setHistory(testTranslations)
    testDispatcher.scheduler.advanceUntilIdle()

    viewModel.deleteTranslation(1)
    testDispatcher.scheduler.advanceUntilIdle()

    viewModel.history.test {
      // Consume initial empty list
      awaitItem()

      val history = awaitItem()
      assertEquals(1, history.size)
      assertEquals("Dog", history[0].sourceWord)
    }
  }
}
