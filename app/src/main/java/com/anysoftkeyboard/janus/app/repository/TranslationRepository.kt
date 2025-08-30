package com.anysoftkeyboard.janus.app.repository

import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation
import com.anysoftkeyboard.janus.network.SearchResult
import com.anysoftkeyboard.janus.network.WikipediaApi
import kotlinx.coroutines.flow.Flow

open class TranslationRepository(
    private val translationDao: TranslationDao,
    private val wikipediaApi: WikipediaApi,
) {
  open fun getHistory(): Flow<List<Translation>> = translationDao.getFullHistory()

  open fun getBookmarks(): Flow<List<Translation>> = translationDao.getBookmarks()

  open suspend fun searchArticles(
      lang: String,
      term: String,
  ): List<SearchResult> {
    val searchResponse = wikipediaApi.search(searchTerm = "$lang $term")
    return searchResponse.query.search ?: throw Exception("Search failed")
  }

  open suspend fun fetchTranslation(
      searchPage: SearchResult,
      sourceLang: String,
      targetLang: String
  ): Translation {
    val langLinksResponse = wikipediaApi.getLangLinks(searchPage.pageid)
    val page =
        langLinksResponse.query.pages.values.firstOrNull() ?: throw Exception("Page not found")
    val langLinks = page.langlinks ?: throw Exception("langLinks not found")

    val targetLink =
        langLinks.find { it.lang == targetLang }
            ?: throw Exception("$targetLang translation not found")

    val translation =
        Translation(
            sourceWord = page.title,
            sourceLangCode = sourceLang,
            sourceArticleUrl = "https://en.wikipedia.org/?curid=${page.pageid}",
            sourceShortDescription = searchPage.snippet,
            sourceSummary = null,
            translatedWord = targetLink.title,
            targetLangCode = targetLang,
            targetArticleUrl = "https://${targetLang}.wikipedia.org/wiki/${targetLink.title}",
            targetShortDescription = null,
            targetSummary = null,
        )
    translationDao.insertTranslation(translation)
    return translation
  }
}
