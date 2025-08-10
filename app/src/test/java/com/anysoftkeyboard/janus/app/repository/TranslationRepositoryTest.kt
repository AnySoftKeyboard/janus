package com.anysoftkeyboard.janus.app.repository

import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation
import com.anysoftkeyboard.janus.network.ContinueData
import com.anysoftkeyboard.janus.network.Query
import com.anysoftkeyboard.janus.network.SearchInfo
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

    assertEquals(listOf(translation), result)
    verify(wikipediaApi, never()).search("$lang $term")
    verify(translationDao, never()).insertTranslations(any())
  }

  @Test
  fun `test search, term not in db`() = runTest {
    val term = "term"
    val lang = "en"
    val searchResult =
        SearchResult(
            ns = 0,
            title = "title",
            pageid = 1,
            size = 1,
            wordcount = 1,
            snippet = "snippet",
            timestamp = "2025-01-01T00:00:00Z")
    val query =
        Query(
            searchinfo = SearchInfo(totalhits = 1, suggestion = null, suggestionsnippet = null),
            search = listOf(searchResult))
    val searchResponse =
        SearchResponse(
            batchcomplete = "",
            continueData = ContinueData(sroffset = 1, continueVal = "-|||"),
            query = query)

    whenever(translationDao.findTranslation(term, lang, lang)).thenReturn(null)
    whenever(wikipediaApi.search(searchTerm = "$lang $term")).thenReturn(searchResponse)

    val result = repository.search(lang, term)

    assertEquals(1, result.size)
    assertEquals("title", result[0].sourceWord)
    assertEquals(lang, result[0].sourceLangCode)
    assertEquals("snippet", result[0].sourceShortDescription)
    verify(translationDao).insertTranslations(any())
  }
}
