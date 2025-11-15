package com.anysoftkeyboard.janus.app.repository

import android.util.Log
import com.anysoftkeyboard.janus.app.di.LangWikipediaFactory
import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation
import kotlinx.coroutines.flow.Flow

data class OptionalSourceTerm(
    val pageid: Long,
    val title: String,
    val snippet: String,
    val availableLanguages: List<String>
)

open class TranslationRepository(
    private val translationDao: TranslationDao,
    private val wikipediaApi: LangWikipediaFactory,
) {
  open fun getHistory(): Flow<List<Translation>> = translationDao.getFullHistory()

  open fun getBookmarks(): Flow<List<Translation>> = translationDao.getBookmarks()

  open suspend fun searchArticles(
      sourceLang: String,
      term: String,
  ): List<OptionalSourceTerm> {
    val api = wikipediaApi.createWikipediaApi(sourceLang)
    val searchResponse = api.search(searchTerm = term)
    // From here, we want to only get the Disambiguation pages (unless there are none.
    return searchResponse.query?.search?.let {
      // If search returned empty list, return empty list immediately
      if (it.isEmpty()) {
        return emptyList()
      }
      val articlesLinks = api.getAllInfo(it.map { p -> p.pageid }.joinToString("|"))
      val disambArticles =
          articlesLinks.query?.pages?.values?.filter { p -> p.pageProps?.disambiguation != null }
              ?: emptyList()
      if (disambArticles.isNotEmpty()) {
        // since we have disambiguation articles, we want to return their links
        val links = api.getLinks(disambArticles.map { p -> p.pageid }.joinToString("|"))
        val titlesOfLinks =
            links.query?.pages?.values?.map { p -> p.links?.map { l -> l.title } ?: listOf() }
                ?: emptyList()
        val flattenedTitles = titlesOfLinks.flatten()
        // If no titles found, return empty list to avoid API call with empty titles parameter
        if (flattenedTitles.isEmpty()) {
          return emptyList()
        }
        val fullLinks = api.getLangLinksForTitles(flattenedTitles.joinToString("|"))
        // return the links as search results
        fullLinks.query?.pages?.values?.map { p ->
          OptionalSourceTerm(
              pageid = p.pageid,
              title = p.title,
              snippet = "",
              availableLanguages = p.langLinks?.map { it.lang } ?: emptyList())
        } ?: emptyList()
      } else {
        // I guess this is what it is.
        it.map { searchResponse ->
          val pageData = articlesLinks.query?.pages?.get(searchResponse.pageid.toString())
          OptionalSourceTerm(
              pageid = searchResponse.pageid,
              title = searchResponse.title,
              snippet = searchResponse.snippet,
              availableLanguages = pageData?.langLinks?.map { it.lang } ?: emptyList())
        }
      }
    } ?: emptyList() // Return empty list when no results found
  }

  open suspend fun fetchTranslations(
      searchPage: OptionalSourceTerm,
      sourceLang: String,
      targetLang: String
  ): List<Translation> {
    val langLinksResponse =
        wikipediaApi.createWikipediaApi(sourceLang).getAllInfo(searchPage.pageid.toString())
    val page =
        langLinksResponse.query?.pages?.values?.firstOrNull() ?: throw Exception("Page not found")
    val langLinks = page.langLinks ?: throw Exception("langLinks not found")
    Log.i("TranslationRepository", "langLinks: ${langLinks.size}")

    // Filter to only the target language the user requested
    val targetLangLinks = langLinks.filter { it.lang == targetLang }
    if (targetLangLinks.isEmpty()) {
      // Target language not available, return all available translations for error handling
      return langLinks.map { link ->
        Translation(
            sourceWord = page.title,
            sourceLangCode = sourceLang,
            sourceArticleUrl = "https://${sourceLang}.wikipedia.org/?curid=${page.pageid}",
            sourceShortDescription = page.pageProps?.wikibaseShortdesc,
            sourceSummary = searchPage.snippet,
            translatedWord = link.title,
            targetLangCode = link.lang,
            targetArticleUrl = "https://${link.lang}.wikipedia.org/wiki/${link.title}",
            targetShortDescription = null,
            targetSummary = null,
        )
      }
    }

    // Fetch target article details for only the requested target language
    val titles = targetLangLinks.joinToString("|") { it.title }
    val targetApi = wikipediaApi.createWikipediaApi(targetLang)
    val detailsResponse = targetApi.getArticleDetails(titles)
    val targetArticleDetails =
        detailsResponse.query?.pages?.values?.associateBy { it.title } ?: emptyMap()

    return targetLangLinks
        .map { link ->
          val targetDetails = targetArticleDetails[link.title]
          Translation(
              sourceWord = page.title,
              sourceLangCode = sourceLang,
              sourceArticleUrl = "https://${sourceLang}.wikipedia.org/?curid=${page.pageid}",
              sourceShortDescription = page.pageProps?.wikibaseShortdesc,
              sourceSummary = searchPage.snippet,
              translatedWord = link.title,
              targetLangCode = link.lang,
              targetArticleUrl = "https://${link.lang}.wikipedia.org/wiki/${link.title}",
              targetShortDescription = targetDetails?.pageProps?.wikibaseShortdesc,
              targetSummary = targetDetails?.extract,
          )
        }
        .also { it.forEach { t -> translationDao.insertTranslation(t) } }
  }
}
