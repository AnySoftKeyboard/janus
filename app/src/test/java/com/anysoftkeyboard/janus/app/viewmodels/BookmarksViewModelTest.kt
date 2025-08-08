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
class BookmarksViewModelTest {

  private lateinit var viewModel: BookmarksViewModel
  private lateinit var fakeRepository: FakeTranslationRepository

  private val testDispatcher = StandardTestDispatcher()

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    fakeRepository = FakeTranslationRepository(mock(), mock())
    viewModel = BookmarksViewModel(fakeRepository)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `bookmarks are empty by default`() = runTest {
    assertEquals(emptyList<Translation>(), viewModel.bookmarks.value)
  }

  @Test
  fun `bookmarks updates when repository bookmarks changes`() = runTest {
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
                targetSummary = "summary1_es",
                isFavorite = true),
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
                targetSummary = "summary2_es",
                isFavorite = true))
    fakeRepository.setBookmarks(testTranslations)
    testDispatcher.scheduler.advanceUntilIdle()

    assertEquals(testTranslations.size, viewModel.bookmarks.value.size)
    for (i in testTranslations.indices) {
      assertEquals(testTranslations[i].sourceWord, viewModel.bookmarks.value[i].sourceWord)
      assertEquals(
          testTranslations[i].sourceLangCode, viewModel.bookmarks.value[i].sourceLangCode)
      assertEquals(
          testTranslations[i].translatedWord, viewModel.bookmarks.value[i].translatedWord)
      assertEquals(
          testTranslations[i].targetLangCode, viewModel.bookmarks.value[i].targetLangCode)
      assertEquals(testTranslations[i].isFavorite, viewModel.bookmarks.value[i].isFavorite)
    }
  }
}
