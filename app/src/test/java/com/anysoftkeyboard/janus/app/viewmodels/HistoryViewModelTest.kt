package com.anysoftkeyboard.janus.app.viewmodels

import com.anysoftkeyboard.janus.app.repository.FakeTranslationRepository
import com.anysoftkeyboard.janus.database.entities.Translation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
    fakeRepository = FakeTranslationRepository(mock(), mock())
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
    fakeRepository.setHistory(testTranslations)
    testDispatcher.scheduler.advanceUntilIdle()

    assertEquals(testTranslations.size, viewModel.history.value.size)
    for (i in testTranslations.indices) {
      assertEquals(testTranslations[i].sourceWord, viewModel.history.value[i].sourceWord)
      assertEquals(testTranslations[i].sourceLangCode, viewModel.history.value[i].sourceLangCode)
      assertEquals(testTranslations[i].translatedWord, viewModel.history.value[i].translatedWord)
      assertEquals(testTranslations[i].targetLangCode, viewModel.history.value[i].targetLangCode)
      assertEquals(testTranslations[i].isFavorite, viewModel.history.value[i].isFavorite)
    }
  }
}
