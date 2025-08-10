package com.anysoftkeyboard.janus.app.repository

import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.network.ContinueData
import com.anysoftkeyboard.janus.network.LangLink
import com.anysoftkeyboard.janus.network.LangLinksQuery
import com.anysoftkeyboard.janus.network.LangLinksResponse
import com.anysoftkeyboard.janus.network.PageLangLinks
import com.anysoftkeyboard.janus.network.Query
import com.anysoftkeyboard.janus.network.SearchInfo
import com.anysoftkeyboard.janus.network.SearchResponse
import com.anysoftkeyboard.janus.network.SearchResult
import com.anysoftkeyboard.janus.network.WikipediaApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
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
  fun `test searchArticles`() = runTest {
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

    whenever(wikipediaApi.search(searchTerm = "$lang $term")).thenReturn(searchResponse)

    val result = repository.searchArticles(lang, term)

    assertEquals(1, result.size)
    assertEquals("title", result[0].title)
  }

  @Test
  fun `test fetchTranslation`() = runTest {
    val pageId = 1L
    val sourceLang = "en"
    val targetLang = "he"
    val langLink = LangLink(lang = targetLang, title = "כותרת")
    val pageLangLinks =
        PageLangLinks(pageid = pageId, ns = 0, title = "title", langlinks = listOf(langLink))
    val langLinksQuery = LangLinksQuery(pages = mapOf(pageId.toString() to pageLangLinks))
    val langLinksResponse = LangLinksResponse(query = langLinksQuery)

    whenever(wikipediaApi.getLangLinks(pageId)).thenReturn(langLinksResponse)

    val result = repository.fetchTranslation(pageId, sourceLang, targetLang)

    assertNotNull(result)
    assertEquals("title", result!!.sourceWord)
    assertEquals(sourceLang, result.sourceLangCode)
    assertEquals("כותרת", result.translatedWord)
    assertEquals(targetLang, result.targetLangCode)
    verify(translationDao).insertTranslation(any())
  }
}
