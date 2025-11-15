package com.anysoftkeyboard.janus.app.repository

import com.anysoftkeyboard.janus.app.di.LangWikipediaFactory
import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.network.LangLink
import com.anysoftkeyboard.janus.network.LangLinksQuery
import com.anysoftkeyboard.janus.network.LangLinksResponse
import com.anysoftkeyboard.janus.network.Link
import com.anysoftkeyboard.janus.network.PageLangLinks
import com.anysoftkeyboard.janus.network.PageProps
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
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class TranslationRepositoryTest {

  private lateinit var repository: TranslationRepository
  private lateinit var translationDao: TranslationDao
  private lateinit var wikipediaApiFactory: LangWikipediaFactory
  private lateinit var wikipediaApi: WikipediaApi

  @Before
  fun setup() {
    translationDao = mock()
    wikipediaApi = mock()
    wikipediaApiFactory = mock()
    whenever(wikipediaApiFactory.createWikipediaApi(any())).thenReturn(wikipediaApi)
    repository = TranslationRepository(translationDao, wikipediaApiFactory)
  }

  @Test
  fun `test searchArticles`() = runTest {
    val term = "term"
    val lang = "en"
    val searchResult = SearchResult(title = "title", pageid = 1, snippet = "snippet")
    val query =
        Query(
            searchinfo = SearchInfo(totalhits = 1, suggestion = null, suggestionsnippet = null),
            search = listOf(searchResult))
    val searchResponse = SearchResponse(query = query)
    val pageLangLinks =
        PageLangLinks(
            pageid = 1,
            ns = 0,
            title = "title",
            langLinks =
                listOf(
                    LangLink(lang = "he", title = "כותרת"), LangLink(lang = "fr", title = "titre")),
            pageProps = null,
            links = null, extract = null)
    val langLinksResponse =
        LangLinksResponse(query = LangLinksQuery(pages = mapOf("1" to pageLangLinks)))

    whenever(wikipediaApi.search(searchTerm = term)).thenReturn(searchResponse)
    whenever(wikipediaApi.getAllInfo(any())).thenReturn(langLinksResponse)

    val result = repository.searchArticles(lang, term)

    assertEquals(1, result.size)
    assertEquals("title", result[0].title)
    assertEquals(listOf("he", "fr"), result[0].availableLanguages)
  }

  @Test
  fun `test fetchTranslations`() = runTest {
    val pageId = 1L
    val sourceLang = "en"
    val targetLang = "he"
    val langLink = LangLink(lang = targetLang, title = "כותרת")
    val pageLangLinks =
        PageLangLinks(
            pageid = pageId,
            ns = 0,
            title = "title",
            langLinks = listOf(langLink),
            pageProps = PageProps(disambiguation = null, wikibaseShortdesc = "short description"),
            links = null,
            extract = null)
    val langLinksQuery = LangLinksQuery(pages = mapOf(pageId.toString() to pageLangLinks))
    val langLinksResponse = LangLinksResponse(query = langLinksQuery)
    val optionalSourceTerm =
        OptionalSourceTerm(
            pageid = pageId,
            title = "title",
            snippet = "snippet",
            availableLanguages = listOf(targetLang))

    // Mock target article details
    val targetArticlePage =
        PageLangLinks(
            pageid = 2L,
            ns = 0,
            title = "כותרת",
            langLinks = null,
            pageProps = PageProps(disambiguation = null, wikibaseShortdesc = "target description"),
            links = null,
            extract = "Target article extract")
    val targetDetailsResponse =
        LangLinksResponse(query = LangLinksQuery(pages = mapOf("2" to targetArticlePage)))

    whenever(wikipediaApi.getAllInfo(pageId.toString())).thenReturn(langLinksResponse)
    whenever(wikipediaApi.getArticleDetails("כותרת")).thenReturn(targetDetailsResponse)

    val result = repository.fetchTranslations(optionalSourceTerm, sourceLang)

    assertNotNull(result)
    assertEquals(1, result.size)
    assertEquals("title", result[0].sourceWord)
    assertEquals(sourceLang, result[0].sourceLangCode)
    assertEquals("כותרת", result[0].translatedWord)
    assertEquals(targetLang, result[0].targetLangCode)
    // Verify URLs are constructed correctly
    assertEquals("https://en.wikipedia.org/?curid=1", result[0].sourceArticleUrl)
    assertEquals("https://he.wikipedia.org/wiki/כותרת", result[0].targetArticleUrl)
    // Verify target article details are populated
    assertEquals("target description", result[0].targetShortDescription)
    assertEquals("Target article extract", result[0].targetSummary)
    verify(translationDao).insertTranslation(any())
  }

  @Test
  fun `test fetchTranslations constructs correct Wikipedia URLs for different languages`() =
      runTest {
        val pageId = 42L
        val sourceLang = "fr"
        val targetLang = "de"
        val targetTitle = "Sommer"
        val langLink = LangLink(lang = targetLang, title = targetTitle)
        val pageLangLinks =
            PageLangLinks(
                pageid = pageId,
                ns = 0,
                title = "Été",
                langLinks = listOf(langLink),
                pageProps = null,
                links = null,
                extract = null)
        val langLinksQuery = LangLinksQuery(pages = mapOf(pageId.toString() to pageLangLinks))
        val langLinksResponse = LangLinksResponse(query = langLinksQuery)
        val optionalSourceTerm =
            OptionalSourceTerm(
                pageid = pageId,
                title = "Été",
                snippet = "snippet",
                availableLanguages = listOf(targetLang))

        // Mock target article details
        val targetArticlePage =
            PageLangLinks(
                pageid = 43L,
                ns = 0,
                title = targetTitle,
                langLinks = null,
                pageProps = null,
                links = null,
                extract = null)
        val targetDetailsResponse =
            LangLinksResponse(query = LangLinksQuery(pages = mapOf("43" to targetArticlePage)))

        whenever(wikipediaApi.getAllInfo(pageId.toString())).thenReturn(langLinksResponse)
        whenever(wikipediaApi.getArticleDetails(targetTitle)).thenReturn(targetDetailsResponse)

        val result = repository.fetchTranslations(optionalSourceTerm, sourceLang)

        // Verify source URL uses curid format
        assertEquals("https://fr.wikipedia.org/?curid=42", result[0].sourceArticleUrl)
        // Verify target URL uses wiki/title format
        assertEquals("https://de.wikipedia.org/wiki/Sommer", result[0].targetArticleUrl)
      }

  @Test
  fun `test fetchTranslations constructs correct URLs with special characters in title`() =
      runTest {
        val pageId = 123L
        val sourceLang = "en"
        val targetLang = "es"
        val targetTitle = "Año nuevo"
        val langLink = LangLink(lang = targetLang, title = targetTitle)
        val pageLangLinks =
            PageLangLinks(
                pageid = pageId,
                ns = 0,
                title = "New Year",
                langLinks = listOf(langLink),
                pageProps = null,
                links = null, extract = null)
        val langLinksQuery = LangLinksQuery(pages = mapOf(pageId.toString() to pageLangLinks))
        val langLinksResponse = LangLinksResponse(query = langLinksQuery)
        val optionalSourceTerm =
            OptionalSourceTerm(
                pageid = pageId,
                title = "New Year",
                snippet = "snippet",
                availableLanguages = listOf(targetLang))

        // Mock target article details
        val targetArticlePage =
            PageLangLinks(
                pageid = 124L,
                ns = 0,
                title = targetTitle,
                langLinks = null,
                pageProps = null,
                links = null,
                extract = null)
        val targetDetailsResponse =
            LangLinksResponse(query = LangLinksQuery(pages = mapOf("124" to targetArticlePage)))

        whenever(wikipediaApi.getAllInfo(pageId.toString())).thenReturn(langLinksResponse)
        whenever(wikipediaApi.getArticleDetails(targetTitle)).thenReturn(targetDetailsResponse)

        val result = repository.fetchTranslations(optionalSourceTerm, sourceLang)

        // Verify URLs are constructed with the title as provided (Wikipedia handles encoding)
        assertEquals("https://en.wikipedia.org/?curid=123", result[0].sourceArticleUrl)
        assertEquals("https://es.wikipedia.org/wiki/Año nuevo", result[0].targetArticleUrl)
      }

  @Test
  fun `test searchArticles with no available translations`() = runTest {
    val term = "rare"
    val lang = "en"
    val searchResult = SearchResult(title = "Rare Article", pageid = 1, snippet = "snippet")
    val query =
        Query(
            searchinfo = SearchInfo(totalhits = 1, suggestion = null, suggestionsnippet = null),
            search = listOf(searchResult))
    val searchResponse = SearchResponse(query = query)
    val pageLangLinks =
        PageLangLinks(
            pageid = 1,
            ns = 0,
            title = "Rare Article",
            langLinks = emptyList(), // No translations available
            pageProps = null,
            links = null, extract = null)
    val langLinksResponse =
        LangLinksResponse(query = LangLinksQuery(pages = mapOf("1" to pageLangLinks)))

    whenever(wikipediaApi.search(searchTerm = term)).thenReturn(searchResponse)
    whenever(wikipediaApi.getAllInfo(any())).thenReturn(langLinksResponse)

    val result = repository.searchArticles(lang, term)

    assertEquals(1, result.size)
    assertEquals("Rare Article", result[0].title)
    assertEquals(emptyList<String>(), result[0].availableLanguages)
  }

  @Test
  fun `test searchArticles with null langLinks`() = runTest {
    val term = "test"
    val lang = "en"
    val searchResult = SearchResult(title = "Test Article", pageid = 1, snippet = "snippet")
    val query =
        Query(
            searchinfo = SearchInfo(totalhits = 1, suggestion = null, suggestionsnippet = null),
            search = listOf(searchResult))
    val searchResponse = SearchResponse(query = query)
    val pageLangLinks =
        PageLangLinks(
            pageid = 1,
            ns = 0,
            title = "Test Article",
            langLinks = null, // null langLinks
            pageProps = null,
            links = null, extract = null)
    val langLinksResponse =
        LangLinksResponse(query = LangLinksQuery(pages = mapOf("1" to pageLangLinks)))

    whenever(wikipediaApi.search(searchTerm = term)).thenReturn(searchResponse)
    whenever(wikipediaApi.getAllInfo(any())).thenReturn(langLinksResponse)

    val result = repository.searchArticles(lang, term)

    assertEquals(1, result.size)
    assertEquals("Test Article", result[0].title)
    assertEquals(emptyList<String>(), result[0].availableLanguages)
  }

  @Test
  fun `test searchArticles with mixed translations`() = runTest {
    val term = "test"
    val lang = "en"
    val searchResult1 = SearchResult(title = "Article 1", pageid = 1, snippet = "snippet1")
    val searchResult2 = SearchResult(title = "Article 2", pageid = 2, snippet = "snippet2")
    val searchResult3 = SearchResult(title = "Article 3", pageid = 3, snippet = "snippet3")
    val query =
        Query(
            searchinfo = SearchInfo(totalhits = 3, suggestion = null, suggestionsnippet = null),
            search = listOf(searchResult1, searchResult2, searchResult3))
    val searchResponse = SearchResponse(query = query)
    val pageLangLinks1 =
        PageLangLinks(
            pageid = 1,
            ns = 0,
            title = "Article 1",
            langLinks =
                listOf(
                    LangLink(lang = "he", title = "מאמר 1"),
                    LangLink(lang = "fr", title = "Article 1")),
            pageProps = null,
            links = null, extract = null)
    val pageLangLinks2 =
        PageLangLinks(
            pageid = 2,
            ns = 0,
            title = "Article 2",
            langLinks = emptyList(), // No translations
            pageProps = null,
            links = null, extract = null)
    val pageLangLinks3 =
        PageLangLinks(
            pageid = 3,
            ns = 0,
            title = "Article 3",
            langLinks = listOf(LangLink(lang = "es", title = "Artículo 3")),
            pageProps = null,
            links = null, extract = null)
    val langLinksResponse =
        LangLinksResponse(
            query =
                LangLinksQuery(
                    pages =
                        mapOf("1" to pageLangLinks1, "2" to pageLangLinks2, "3" to pageLangLinks3)))

    whenever(wikipediaApi.search(searchTerm = term)).thenReturn(searchResponse)
    whenever(wikipediaApi.getAllInfo(any())).thenReturn(langLinksResponse)

    val result = repository.searchArticles(lang, term)

    assertEquals(3, result.size)
    assertEquals("Article 1", result[0].title)
    assertEquals(listOf("he", "fr"), result[0].availableLanguages)
    assertEquals("Article 2", result[1].title)
    assertEquals(emptyList<String>(), result[1].availableLanguages)
    assertEquals("Article 3", result[2].title)
    assertEquals(listOf("es"), result[2].availableLanguages)
  }

  @Test
  fun `test searchArticles when pageData not found in response`() = runTest {
    val term = "test"
    val lang = "en"
    val searchResult = SearchResult(title = "Missing Article", pageid = 999, snippet = "snippet")
    val query =
        Query(
            searchinfo = SearchInfo(totalhits = 1, suggestion = null, suggestionsnippet = null),
            search = listOf(searchResult))
    val searchResponse = SearchResponse(query = query)
    // Response doesn't contain page with id 999
    val langLinksResponse = LangLinksResponse(query = LangLinksQuery(pages = emptyMap()))

    whenever(wikipediaApi.search(searchTerm = term)).thenReturn(searchResponse)
    whenever(wikipediaApi.getAllInfo(any())).thenReturn(langLinksResponse)

    val result = repository.searchArticles(lang, term)

    assertEquals(1, result.size)
    assertEquals("Missing Article", result[0].title)
    assertEquals(emptyList<String>(), result[0].availableLanguages)
  }

  @Test
  fun `test searchArticles with null query returns empty list`() = runTest {
    val term = "nonexistentterm"
    val lang = "en"
    // Wikipedia returns a response with null query when no results found
    val searchResponse = SearchResponse(query = null)

    whenever(wikipediaApi.search(searchTerm = term)).thenReturn(searchResponse)

    val result = repository.searchArticles(lang, term)

    assertEquals(0, result.size)
  }

  @Test
  fun `test searchArticles with null search in query returns empty list`() = runTest {
    val term = "test"
    val lang = "en"
    // Wikipedia returns a query with null search when no results found
    val query = Query(searchinfo = null, search = null)
    val searchResponse = SearchResponse(query = query)

    whenever(wikipediaApi.search(searchTerm = term)).thenReturn(searchResponse)

    val result = repository.searchArticles(lang, term)

    assertEquals(0, result.size)
  }

  @Test
  fun `test searchArticles with empty search list returns empty list`() = runTest {
    val term = "test"
    val lang = "en"
    // Wikipedia returns a query with empty search list (no results)
    // This happens when search term exists but has no actual results
    val query = Query(searchinfo = SearchInfo(0, null, null), search = emptyList())
    val searchResponse = SearchResponse(query = query)

    whenever(wikipediaApi.search(searchTerm = term)).thenReturn(searchResponse)

    val result = repository.searchArticles(lang, term)

    // Should return empty list without calling getAllInfo (which would fail with empty pageids)
    assertEquals(0, result.size)
  }

  @Test
  fun `test searchArticles disambiguation path with langlinks`() = runTest {
    val term = "test"
    val lang = "en"
    // Search returns a disambiguation page
    val searchResult =
        SearchResult(title = "Test (disambiguation)", pageid = 100, snippet = "snippet")
    val query =
        Query(
            searchinfo = SearchInfo(totalhits = 1, suggestion = null, suggestionsnippet = null),
            search = listOf(searchResult))
    val searchResponse = SearchResponse(query = query)

    // getAllInfo returns the disambiguation page
    val disambPage =
        PageLangLinks(
            pageid = 100,
            ns = 0,
            title = "Test (disambiguation)",
            langLinks = listOf(LangLink(lang = "he", title = "מבחן (פירושונים)")),
            pageProps =
                PageProps(disambiguation = "", wikibaseShortdesc = null), // disambiguation marker
            links = null, extract = null)
    val disambResponse =
        LangLinksResponse(query = LangLinksQuery(pages = mapOf("100" to disambPage)))

    // getLinks returns the linked articles from the disambiguation page
    val linkedPage1 =
        PageLangLinks(
            pageid = 101,
            ns = 0,
            title = "Test Article 1",
            langLinks = null,
            pageProps = null,
            links = listOf(Link(title = "Test Article 1"), Link(title = "Test Article 2")),
            extract = null)
    val linksResponse =
        LangLinksResponse(query = LangLinksQuery(pages = mapOf("101" to linkedPage1)))

    // getLangLinksForTitles returns the final articles with their translations
    val finalArticle1 =
        PageLangLinks(
            pageid = 201,
            ns = 0,
            title = "Test Article 1",
            langLinks =
                listOf(
                    LangLink(lang = "he", title = "מאמר מבחן 1"),
                    LangLink(lang = "fr", title = "Article de test 1")),
            pageProps = null,
            links = null, extract = null)
    val finalArticle2 =
        PageLangLinks(
            pageid = 202,
            ns = 0,
            title = "Test Article 2",
            langLinks = listOf(LangLink(lang = "es", title = "Artículo de prueba 2")),
            pageProps = null,
            links = null, extract = null)
    val finalResponse =
        LangLinksResponse(
            query = LangLinksQuery(pages = mapOf("201" to finalArticle1, "202" to finalArticle2)))

    whenever(wikipediaApi.search(searchTerm = term)).thenReturn(searchResponse)
    whenever(wikipediaApi.getAllInfo("100")).thenReturn(disambResponse)
    whenever(wikipediaApi.getLinks("100")).thenReturn(linksResponse)
    whenever(wikipediaApi.getLangLinksForTitles("Test Article 1|Test Article 2"))
        .thenReturn(finalResponse)

    val result = repository.searchArticles(lang, term)

    // Should return the linked articles with their available languages
    assertEquals(2, result.size)
    assertEquals("Test Article 1", result[0].title)
    assertEquals(listOf("he", "fr"), result[0].availableLanguages)
    assertEquals("Test Article 2", result[1].title)
    assertEquals(listOf("es"), result[1].availableLanguages)
  }

  @Test
  fun `test searchArticles with disambiguation page but no links returns empty list`() = runTest {
    val term = "what's up"
    val lang = "en"

    // Search returns a disambiguation page
    val searchResult = SearchResult(title = "What's up", pageid = 100, snippet = "")
    val query = Query(searchinfo = SearchInfo(1, null, null), search = listOf(searchResult))
    val searchResponse = SearchResponse(query = query)

    // getAllInfo returns the disambiguation page
    val disambPage =
        PageLangLinks(
            pageid = 100,
            ns = 0,
            title = "What's up",
            langLinks = null,
            pageProps = PageProps(disambiguation = "", wikibaseShortdesc = null),
            links = null, extract = null)
    val disambResponse =
        LangLinksResponse(query = LangLinksQuery(pages = mapOf("100" to disambPage)))

    // getLinks returns no links (empty pages map or pages with no links)
    val linksResponse =
        LangLinksResponse(
            query = LangLinksQuery(pages = mapOf("100" to disambPage.copy(links = emptyList()))))

    whenever(wikipediaApi.search(searchTerm = term)).thenReturn(searchResponse)
    whenever(wikipediaApi.getAllInfo("100")).thenReturn(disambResponse)
    whenever(wikipediaApi.getLinks("100")).thenReturn(linksResponse)

    val result = repository.searchArticles(lang, term)

    // Should return empty list without calling getLangLinksForTitles with empty string
    assertEquals(0, result.size)
  }
}
