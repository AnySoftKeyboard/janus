package com.anysoftkeyboard.janus.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anysoftkeyboard.janus.database.entities.Translation
import kotlinx.coroutines.flow.Flow

@Dao
interface TranslationDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTranslation(translation: Translation)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTranslations(translations: List<Translation>)

  @Query(
      "SELECT * FROM translation_history WHERE sourceWord = :sourceWord AND sourceLangCode = :sourceLang AND targetLangCode = :targetLang LIMIT 1"
  )
  suspend fun findTranslation(
      sourceWord: String,
      sourceLang: String,
      targetLang: String,
  ): Translation?

  @Query("SELECT * FROM translation_history ORDER BY timestamp DESC")
  fun getFullHistory(): Flow<List<Translation>>

  @Query(
      "SELECT * FROM translation_history WHERE sourceWord LIKE '%' || :query || '%' OR translatedWord LIKE '%' || :query || '%' ORDER BY timestamp DESC"
  )
  fun searchHistory(query: String): Flow<List<Translation>>

  @Query("SELECT * FROM translation_history WHERE isFavorite = 1 ORDER BY timestamp DESC")
  fun getBookmarks(): Flow<List<Translation>>

  @Delete suspend fun deleteTranslation(translation: Translation)

  @Query("DELETE FROM translation_history WHERE id IN (:ids)")
  suspend fun deleteTranslationsByIds(ids: List<Int>)

  @Query("DELETE FROM translation_history WHERE isFavorite = 0") suspend fun clearAllHistory()
}
