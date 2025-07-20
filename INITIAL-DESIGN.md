# Janus:: Initial Design Document
This document outlines the technical design for the WikiTranslate Android app (codename: Janus). It is intended for developers implementing the application.

# Architecture Overview
The project will follow modern Android architecture best practices, utilizing an MVVM (Model-View-ViewModel) pattern with a Repository as the single source of truth.
* Data Flow: UI (Compose) -> ViewModel -> Repository -> (Room Database | Retrofit API)
* UI Layer (Jetpack Compose): Renders the UI and sends user events to the ViewModel.
* ViewModel: Holds UI state (StateFlow), handles user events, and calls the Repository.
* Repository: The single source of truth. It abstracts the data sources and decides whether to fetch data from the local database or the remote API.
* Dependency Injection: Hilt will be used to manage and provide all dependencies.

## Database Design (Room)
The app will use a Room database to persist user history and bookmarks.

### Translation Entity
The database will be built around a single, denormalized table for simplicity and performance.

```kotlin
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
```

### TranslationDao Interface

```kotlin
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
```

## Design Rationale: Single-Table Approach
The database uses a denormalized single table. While this creates some data redundancy, it was chosen for two main reasons:
* Simplicity: It dramatically simplifies data management (INSERT, SELECT, etc.).
* Performance: Reading data for UI lists is highly efficient as it avoids complex and slower JOIN operations.
🌐 Networking and Caching
API Client: Retrofit will be used to make direct REST calls to the official MediaWiki API.
Caching Strategy: A two-layer cache will be implemented for efficiency and offline access.
Database First: The Repository will first query the Room database for a cached translation before making a network call.
HTTP Cache: OkHttp will be configured with an HTTP cache to respect server cache headers.
🧠 Key Logic and UI/UX
Reverse Lookups: The app will not assume that a translation is symmetrical (A -> B ≠ B -> A). A reverse translation will always be treated as a new, independent lookup to ensure accuracy.
Navigation: A Bottom Navigation Bar will provide access to the three main screens: Translate, History, and Bookmarked.
History Management: Users can delete single items via Swipe-to-Delete. A Multi-select mode (triggered by long-press) will be available on the History screen for bulk deletion.
Attribution: An "About" screen will be included, accessible from an overflow menu. It will display a list of open-source libraries using Google's com.google.android.gms:oss-licenses-plugin to meet attribution requirements.
User Preferences: Jetpack DataStore will be used to persist the user's last-selected FROM and TO languages.
