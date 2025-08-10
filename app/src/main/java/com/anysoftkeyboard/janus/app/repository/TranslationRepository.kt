package com.anysoftkeyboard.janus.app.repository

import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation
import com.anysoftkeyboard.janus.network.WikipediaApi
import kotlinx.coroutines.flow.Flow

open class TranslationRepository(
    private val translationDao: TranslationDao,
    private val wikipediaApi: WikipediaApi,
) {
  open fun getHistory(): Flow<List<Translation>> = translationDao.getFullHistory()

  open fun getBookmarks(): Flow<List<Translation>> = translationDao.getBookmarks()

  open suspend fun search(
      lang: String,
      term: String,
  ): List<Translation> {
    val local = translationDao.findTranslation(term, lang, lang)
    if (local != null) return listOf(local)

    val searchResponse = wikipediaApi.search(searchTerm = "$lang $term")
    val translations =
        searchResponse.query.search.map { searchResult ->
          Translation(
              sourceWord = searchResult.title,
              sourceLangCode = lang,
              sourceArticleUrl = "https://en.wikipedia.org/?curid=${searchResult.pageid}",
              sourceShortDescription = searchResult.snippet,
              sourceSummary = "summary",
              translatedWord = "translated",
              targetLangCode = "he",
              targetArticleUrl = "https://he.wikipedia.org/wiki/$term",
              targetShortDescription = "hebrew desc",
              targetSummary = "hebrew summary",
          )
        }
    translationDao.insertTranslations(translations)
    return translations
  }
}
