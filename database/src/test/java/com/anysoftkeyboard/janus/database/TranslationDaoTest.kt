package com.anysoftkeyboard.janus.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TranslationDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var translationDao: TranslationDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        translationDao = database.translationDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private fun createTranslation(
        id: Int = 0,
        sourceWord: String,
        sourceLang: String,
        targetLang: String,
        isFavorite: Boolean = false
    ) = Translation(
        id = id,
        sourceWord = sourceWord,
        sourceLangCode = sourceLang,
        targetLangCode = targetLang,
        sourceArticleUrl = "",
        sourceShortDescription = null,
        sourceSummary = null,
        translatedWord = "",
        targetArticleUrl = "",
        targetShortDescription = null,
        targetSummary = null,
        isFavorite = isFavorite,
        timestamp = System.currentTimeMillis()
    )

    @Test
    fun `test insert and find translation`() = runBlocking {
        val translation = createTranslation(sourceWord = "hello", sourceLang = "en", targetLang = "es")
        translationDao.insertTranslation(translation)

        val found = translationDao.findTranslation("hello", "en", "es")
        assertEquals(translation.sourceWord, found?.sourceWord)
    }

    @Test
    fun `test find non-existent translation`() = runBlocking {
        val found = translationDao.findTranslation("non", "en", "es")
        assertNull(found)
    }

    @Test
    fun `test get full history`() = runBlocking {
        val t1 = createTranslation(sourceWord = "a", sourceLang = "en", targetLang = "es")
        val t2 = createTranslation(sourceWord = "b", sourceLang = "en", targetLang = "es")
        translationDao.insertTranslation(t1)
        translationDao.insertTranslation(t2)

        translationDao.getFullHistory().test {
            val list = awaitItem()
            assertEquals(2, list.size)
        }
    }

    @Test
    fun `test get bookmarks`() = runBlocking {
        val t1 = createTranslation(sourceWord = "a", sourceLang = "en", targetLang = "es", isFavorite = true)
        val t2 = createTranslation(sourceWord = "b", sourceLang = "en", targetLang = "es", isFavorite = false)
        translationDao.insertTranslation(t1)
        translationDao.insertTranslation(t2)

        translationDao.getBookmarks().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("a", list[0].sourceWord)
        }
    }

    @Test
    fun `test delete translation`() = runBlocking {
        val translation = createTranslation(id = 1, sourceWord = "a", sourceLang = "en", targetLang = "es")
        translationDao.insertTranslation(translation)
        translationDao.deleteTranslation(translation.copy(id = 1))

        val found = translationDao.findTranslation("a", "en", "es")
        assertNull(found)
    }

    @Test
    fun `test delete translations by ids`() = runBlocking {
        val t1 = createTranslation(id = 1, sourceWord = "a", sourceLang = "en", targetLang = "es")
        val t2 = createTranslation(id = 2, sourceWord = "b", sourceLang = "en", targetLang = "es")
        translationDao.insertTranslation(t1)
        translationDao.insertTranslation(t2)

        translationDao.deleteTranslationsByIds(listOf(1))
        assertNull(translationDao.findTranslation("a", "en", "es"))
        assertTrue(translationDao.findTranslation("b", "en", "es") != null)
    }

    @Test
    fun `test clear all history`() = runBlocking {
        val t1 = createTranslation(sourceWord = "a", sourceLang = "en", targetLang = "es", isFavorite = true)
        val t2 = createTranslation(sourceWord = "b", sourceLang = "en", targetLang = "es", isFavorite = false)
        translationDao.insertTranslation(t1)
        translationDao.insertTranslation(t2)

        translationDao.clearAllHistory()

        translationDao.getFullHistory().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("a", list[0].sourceWord)
        }
    }
}
