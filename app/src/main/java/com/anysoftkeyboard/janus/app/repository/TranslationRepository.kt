package com.anysoftkeyboard.janus.app.repository

import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation
import com.anysoftkeyboard.janus.network.WikipediaApi
import kotlinx.coroutines.flow.Flow

open class TranslationRepository(
    private val translationDao: TranslationDao,
    private val wikipediaApi: WikipediaApi
) {
  open fun getHistory(): Flow<List<Translation>> = translationDao.getFullHistory()

  open fun getBookmarks(): Flow<List<Translation>> = translationDao.getBookmarks()
}
