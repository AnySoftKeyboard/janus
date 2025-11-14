package com.anysoftkeyboard.janus.app.repository

import com.anysoftkeyboard.janus.app.di.LangWikipediaFactory
import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeTranslationRepository
@Inject
constructor(
    private val translationDao: TranslationDao,
    private val wikipediaApi: LangWikipediaFactory
) : TranslationRepository(translationDao, wikipediaApi) {

  private val _history = MutableStateFlow(emptyList<Translation>())
  private val _bookmarks = MutableStateFlow(emptyList<Translation>())
  var nextSearchResults: List<OptionalSourceTerm> = emptyList()
  var nextTranslations: List<Translation> = emptyList()

  override fun getHistory(): Flow<List<Translation>> = _history.asStateFlow()

  override fun getBookmarks(): Flow<List<Translation>> = _bookmarks.asStateFlow()

  override suspend fun searchArticles(sourceLang: String, term: String): List<OptionalSourceTerm> {
    return nextSearchResults
  }

  override suspend fun fetchTranslations(
      searchPage: OptionalSourceTerm,
      sourceLang: String
  ): List<Translation> {
    return nextTranslations
  }

  fun setHistory(history: List<Translation>) {
    _history.value = history
  }

  fun setBookmarks(bookmarks: List<Translation>) {
    _bookmarks.value = bookmarks
  }
}
