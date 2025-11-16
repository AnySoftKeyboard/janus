package com.anysoftkeyboard.janus.app.repository

import android.util.Log
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.di.LangWikipediaFactory
import com.anysoftkeyboard.janus.app.util.StringProvider
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
    private val stringProvider: StringProvider,
) {
  open fun getHistory(): Flow<List<Translation>> = translationDao.getFullHistory()

  open fun searchHistory(query: String): Flow<List<Translation>> =
      translationDao.searchHistory(query)

  open fun getBookmarks(): Flow<List<Translation>> = translationDao.getBookmarks()

  open suspend fun searchArticles(
      sourceLang: String,
      term: String,
  ): List<OptionalSourceTerm> {
    val api = wikipediaApi.createWikipediaApi(sourceLang)
    val searchResponse = api.search(searchTerm = term)
    return searchResponse.query?.search?.let { searchResults ->
      // If search returned empty list, return empty list immediately
      if (searchResults.isEmpty()) {
        return emptyList()
      }

      val articlesLinks = api.getAllInfo(searchResults.map { p -> p.pageid }.joinToString("|"))
      val allPages = articlesLinks.query?.pages?.values ?: emptyList()

      // Separate disambiguation and regular articles
      val disambArticles = allPages.filter { p -> p.pageProps?.disambiguation != null }
      val regularArticles = allPages.filter { p -> p.pageProps?.disambiguation == null }

      // Process regular articles
      val regularResults =
          searchResults.mapNotNull { searchResult ->
            val pageData = articlesLinks.query?.pages?.get(searchResult.pageid.toString())
            // Only include if it's not a disambiguation page
            if (pageData?.pageProps?.disambiguation == null) {
              OptionalSourceTerm(
                  pageid = searchResult.pageid,
                  title = searchResult.title,
                  snippet = searchResult.snippet,
                  availableLanguages = pageData?.langLinks?.map { it.lang } ?: emptyList())
            } else {
              null
            }
          }

      // Process disambiguation articles to get their links
      val disambResults =
          if (disambArticles.isNotEmpty()) {
            val links = api.getLinks(disambArticles.map { p -> p.pageid }.joinToString("|"))
            val titlesOfLinks =
                links.query?.pages?.values?.map { p -> p.links?.map { l -> l.title } ?: listOf() }
                    ?: emptyList()
            val flattenedTitles = titlesOfLinks.flatten()

            if (flattenedTitles.isNotEmpty()) {
              val fullLinks = api.getLangLinksForTitles(flattenedTitles.joinToString("|"))
              fullLinks.query?.pages?.values?.map { p ->
                OptionalSourceTerm(
                    pageid = p.pageid,
                    title = p.title,
                    snippet = "",
                    availableLanguages = p.langLinks?.map { it.lang } ?: emptyList())
              } ?: emptyList()
            } else {
              emptyList()
            }
          } else {
            emptyList()
          }

      // Combine both regular articles and disambiguation links
      regularResults + disambResults
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
        langLinksResponse.query?.pages?.values?.firstOrNull()
            ?: throw Exception(stringProvider.getString(R.string.error_page_not_found))
    val langLinks =
        page.langLinks
            ?: throw Exception(stringProvider.getString(R.string.error_langlinks_not_found))
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
