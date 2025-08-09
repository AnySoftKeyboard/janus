package com.anysoftkeyboard.janus.app.repository

import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation
import com.anysoftkeyboard.janus.network.Query
import com.anysoftkeyboard.janus.network.SearchResponse
import com.anysoftkeyboard.janus.network.SearchResult
import com.anysoftkeyboard.janus.network.WikipediaApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TranslationRepositoryTest {

  private lateinit var repository: TranslationRepository
  private lateinit var translationDao: TranslationDao
  private lateinit var wikipediaApi: WikipediaApi

  @Before
  fun setup() {
    translationDao = mock()
    wikipediaApi = mock()
    repository = TranslationRepository(translationDao, wikipediaApi)
  }

  @Test
  fun `test search, term in db`() = runTest {
    val term = "term"
    val lang = "en"
    val translation =
        Translation(
            sourceWord = term,
            sourceLangCode = lang,
            targetLangCode = lang,
            sourceArticleUrl = "",
            sourceShortDescription = "",
            sourceSummary = "",
            translatedWord = "",
            targetArticleUrl = "",
            targetShortDescription = "",
            targetSummary = "")
    whenever(translationDao.findTranslation(term, lang, lang)).thenReturn(translation)

    val result = repository.search(lang, term)

    assertEquals(translation, result)
    verify(wikipediaApi, never()).search(any(), any(), any())
    verify(translationDao, never()).insertTranslation(any())
  }

  @Test
  fun `test search, term not in db`() = runTest {
    val term = "term"
    val lang = "en"
    val searchResult = SearchResult("title", "snippet")
    val query = Query(listOf(searchResult))
    val searchResponse = SearchResponse(query)

    whenever(translationDao.findTranslation(term, lang, lang)).thenReturn(null)
    whenever(wikipediaApi.search(searchTerm = "$lang $term")).thenReturn(searchResponse)

    val result = repository.search(lang, term)

    assertEquals(term, result.sourceWord)
    assertEquals(lang, result.sourceLangCode)
    assertEquals("snippet", result.sourceShortDescription)
    verify(translationDao).insertTranslation(result)
  }
}
