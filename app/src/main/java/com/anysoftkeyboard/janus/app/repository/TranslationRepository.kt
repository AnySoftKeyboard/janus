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
    return searchResponse.query.search ?: emptyList()
  }

  open suspend fun fetchTranslation(
      pageId: Long,
      sourceLang: String,
      targetLang: String
  ): Translation? {
    val langLinksResponse = wikipediaApi.getLangLinks(pageId)
    val page = langLinksResponse.query.pages.values.firstOrNull()
    if (page == null) return null
    val langLinks = page.langlinks
    if (langLinks == null) return null

    val targetLink = langLinks.find { it.lang == targetLang } ?: return null

    val translation =
        Translation(
            sourceWord = page.title,
            sourceLangCode = sourceLang,
            sourceArticleUrl = "https://en.wikipedia.org/?curid=${page.pageid}",
            sourceShortDescription = null,
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
