package com.anysoftkeyboard.janus.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "translation_history")
data class Translation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // --- Source Article ---
    @ColumnInfo(index = true)
    val sourceWord: String,
    @ColumnInfo(index = true)
    val sourceLangCode: String,
    val sourceArticleUrl: String,
    val sourceShortDescription: String?,
    val sourceSummary: String?,

    // --- Target Article ---
    val translatedWord: String,
    @ColumnInfo(index = true)
    val targetLangCode: String,
    val targetArticleUrl: String,
    val targetShortDescription: String?,
    val targetSummary: String?,

    // --- Metadata ---
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)

@Dao
interface TranslationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translation: Translation)

    @Query("SELECT * FROM translation_history WHERE sourceWord = :sourceWord AND sourceLangCode = :sourceLang AND targetLangCode = :targetLang LIMIT 1")
    suspend fun findTranslation(sourceWord: String, sourceLang: String, targetLang: String): Translation?

    @Query("SELECT * FROM translation_history ORDER BY timestamp DESC")
    fun getFullHistory(): Flow<List<Translation>>

    @Query("SELECT * FROM translation_history WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getBookmarks(): Flow<List<Translation>>

    @Delete
    suspend fun deleteTranslation(translation: Translation)

    @Query("DELETE FROM translation_history WHERE id IN (:ids)")
    suspend fun deleteTranslationsByIds(ids: List<Int>)

    @Query("DELETE FROM translation_history WHERE isFavorite = 0")
    suspend fun clearAllHistory()
}

@Database(entities = [Translation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun translationDao(): TranslationDao
}
