package com.anysoftkeyboard.janus.app.viewmodels

import app.cash.turbine.test
import com.anysoftkeyboard.janus.app.repository.FakeTranslationRepository
import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation
import com.anysoftkeyboard.janus.network.SearchResult
import com.anysoftkeyboard.janus.network.WikipediaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock as mockitoMock

@ExperimentalCoroutinesApi
class TranslateViewModelTest {

  private val testDispatcher = StandardTestDispatcher()
  private lateinit var viewModel: TranslateViewModel
  private lateinit var repository: FakeTranslationRepository

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    repository =
        FakeTranslationRepository(mockitoMock<TranslationDao>(), mockitoMock<WikipediaApi>())
    viewModel = TranslateViewModel(repository)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `test searchArticles`() = runTest {
    val searchResults = listOf(SearchResult(0, "title", 1, 1, 1, "snippet", "timestamp"))
    repository.nextSearchResults = searchResults

    viewModel.searchResults.test {
      assertEquals(0, awaitItem().size)
      viewModel.searchArticles("en", "term")
      assertEquals(searchResults, awaitItem())
    }
  }

  @Test
  fun `test fetchTranslation`() = runTest {
    val translation =
        Translation(
            sourceWord = "title",
            sourceLangCode = "en",
            targetLangCode = "he",
            translatedWord = "כותרת",
            sourceArticleUrl = "",
            sourceShortDescription = "",
            sourceSummary = "",
            targetArticleUrl = "",
            targetShortDescription = "",
            targetSummary = "")
    repository.nextTranslation = translation

    viewModel.translation.test {
      assertEquals(null, awaitItem())
      viewModel.fetchTranslation(1, "en", "he")
      assertEquals(translation, awaitItem())
    }
  }

  @Test
  fun `test isLoading state for searchArticles`() = runTest {
    viewModel.isLoading.test {
      assertEquals(false, awaitItem())
      viewModel.searchArticles("en", "term")
      assertEquals(true, awaitItem())
      assertEquals(false, awaitItem())
    }
  }

  @Test
  fun `test isLoading state for fetchTranslation`() = runTest {
    viewModel.isLoading.test {
      assertEquals(false, awaitItem())
      viewModel.fetchTranslation(1, "en", "he")
      assertEquals(true, awaitItem())
      assertEquals(false, awaitItem())
    }
  }

  @Test
  fun `test errorFetchingTranslation state`() = runTest {
    repository.nextTranslation = null // Simulate an error by returning null
    viewModel.errorFetchingTranslation.test {
      assertEquals(false, awaitItem())
      viewModel.fetchTranslation(1, "en", "he")
      assertEquals(true, awaitItem())
      assertEquals(false, awaitItem())
    }
    viewModel.translation.test { assertNull(awaitItem()) }
  }
}
