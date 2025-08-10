package com.anysoftkeyboard.janus.app.repository

import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation
import com.anysoftkeyboard.janus.network.SearchResult
import com.anysoftkeyboard.janus.network.WikipediaApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeTranslationRepository
@Inject
constructor(private val translationDao: TranslationDao, private val wikipediaApi: WikipediaApi) :
    TranslationRepository(translationDao, wikipediaApi) {

  private val _history = MutableStateFlow(emptyList<Translation>())
  private val _bookmarks = MutableStateFlow(emptyList<Translation>())
  var nextSearchResults: List<SearchResult> = emptyList()
  var nextTranslation: Translation? = null

  override fun getHistory(): Flow<List<Translation>> = _history.asStateFlow()

  override fun getBookmarks(): Flow<List<Translation>> = _bookmarks.asStateFlow()

  override suspend fun searchArticles(lang: String, term: String): List<SearchResult> {
    return nextSearchResults
  }

  override suspend fun fetchTranslation(
      pageId: Long,
      sourceLang: String,
      targetLang: String
  ): Translation? {
    return nextTranslation
  }

  fun setHistory(history: List<Translation>) {
    _history.value = history
  }

  fun setBookmarks(bookmarks: List<Translation>) {
    _bookmarks.value = bookmarks
  }
}
