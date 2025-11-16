package com.anysoftkeyboard.janus.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation
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
    database =
        Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
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
      isFavorite: Boolean = false,
      timestamp: Long = System.currentTimeMillis(),
  ) =
      Translation(
          id = id,
          sourceWord = sourceWord,
          sourceLangCode = sourceLang,
          targetLangCode = targetLang,
          sourceArticleUrl = "https://en.wikipedia.org/wiki/$sourceWord",
          sourceShortDescription = "short description for $sourceWord",
          sourceSummary = "summary for $sourceWord",
          translatedWord = "$sourceWord translated",
          targetArticleUrl = "https://$targetLang.wikipedia.org/wiki/$sourceWord",
          targetShortDescription = "short description for $sourceWord in $targetLang",
          targetSummary = "summary for $sourceWord in $targetLang",
          isFavorite = isFavorite,
          timestamp = timestamp,
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
    val t1 =
        createTranslation(sourceWord = "a", sourceLang = "en", targetLang = "es", isFavorite = true)
    val t2 =
        createTranslation(
            sourceWord = "b", sourceLang = "en", targetLang = "es", isFavorite = false)
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
    val translation = createTranslation(sourceWord = "a", sourceLang = "en", targetLang = "es")
    translationDao.insertTranslation(translation)
    val inserted = translationDao.findTranslation("a", "en", "es")!!
    translationDao.deleteTranslation(inserted)

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
  fun `test clear all history leaves bookmarks`() = runBlocking {
    val t1 =
        createTranslation(sourceWord = "a", sourceLang = "en", targetLang = "es", isFavorite = true)
    val t2 =
        createTranslation(
            sourceWord = "b", sourceLang = "en", targetLang = "es", isFavorite = false)
    translationDao.insertTranslation(t1)
    translationDao.insertTranslation(t2)

    translationDao.clearAllHistory()

    translationDao.getFullHistory().test {
      val list = awaitItem()
      assertEquals(1, list.size)
      assertEquals("a", list[0].sourceWord)
    }
  }

  @Test
  fun `test update translation`() = runBlocking {
    val translation = createTranslation(sourceWord = "hello", sourceLang = "en", targetLang = "es")
    translationDao.insertTranslation(translation)

    val inserted = translationDao.findTranslation("hello", "en", "es")!!
    val updated = inserted.copy(isFavorite = true)
    translationDao.insertTranslation(updated)

    val found = translationDao.findTranslation("hello", "en", "es")
    assertEquals(true, found?.isFavorite)
  }

  @Test
  fun `test history is ordered by timestamp desc`() = runBlocking {
    val t1 =
        createTranslation(sourceWord = "a", sourceLang = "en", targetLang = "es", timestamp = 1)
    val t2 =
        createTranslation(sourceWord = "b", sourceLang = "en", targetLang = "es", timestamp = 2)
    translationDao.insertTranslation(t1)
    translationDao.insertTranslation(t2)

    translationDao.getFullHistory().test {
      val list = awaitItem()
      assertEquals("b", list[0].sourceWord)
      assertEquals("a", list[1].sourceWord)
    }
  }

  @Test
  fun `test bookmarks are ordered by timestamp desc`() = runBlocking {
    val t1 =
        createTranslation(
            sourceWord = "a",
            sourceLang = "en",
            targetLang = "es",
            isFavorite = true,
            timestamp = 1)
    val t2 =
        createTranslation(
            sourceWord = "b",
            sourceLang = "en",
            targetLang = "es",
            isFavorite = true,
            timestamp = 2)
    translationDao.insertTranslation(t1)
    translationDao.insertTranslation(t2)

    translationDao.getBookmarks().test {
      val list = awaitItem()
      assertEquals("b", list[0].sourceWord)
      assertEquals("a", list[1].sourceWord)
    }
  }

  @Test
  fun `test find translation is case sensitive`() = runBlocking {
    val translation = createTranslation(sourceWord = "hello", sourceLang = "en", targetLang = "es")
    translationDao.insertTranslation(translation)

    assertNull(translationDao.findTranslation("Hello", "en", "es"))
    assertNull(translationDao.findTranslation("hello", "EN", "es"))
  }

  @Test
  fun `test delete translations by ids with non-existent id`() = runBlocking {
    val t1 = createTranslation(id = 1, sourceWord = "a", sourceLang = "en", targetLang = "es")
    translationDao.insertTranslation(t1)

    translationDao.deleteTranslationsByIds(listOf(1, 2))
    assertNull(translationDao.findTranslation("a", "en", "es"))
  }

  @Test
  fun `test searchHistory finds matches in sourceWord`() = runBlocking {
    val t1 = createTranslation(sourceWord = "Cat", sourceLang = "en", targetLang = "es")
    val t2 = createTranslation(sourceWord = "Dog", sourceLang = "en", targetLang = "es")
    translationDao.insertTranslation(t1)
    translationDao.insertTranslation(t2)

    translationDao.searchHistory("Cat").test {
      val list = awaitItem()
      assertEquals(1, list.size)
      assertEquals("Cat", list[0].sourceWord)
    }
  }

  @Test
  fun `test searchHistory finds matches in translatedWord`() = runBlocking {
    val t1 = createTranslation(sourceWord = "Cat", sourceLang = "en", targetLang = "he")
    val t2 = createTranslation(sourceWord = "Dog", sourceLang = "en", targetLang = "he")
    translationDao.insertTranslation(t1)
    translationDao.insertTranslation(t2)

    translationDao.searchHistory("Dog translated").test {
      val list = awaitItem()
      assertEquals(1, list.size)
      assertEquals("Dog", list[0].sourceWord)
    }
  }

  @Test
  fun `test searchHistory is case insensitive`() = runBlocking {
    val t1 = createTranslation(sourceWord = "Cat", sourceLang = "en", targetLang = "es")
    translationDao.insertTranslation(t1)

    translationDao.searchHistory("cat").test {
      val list = awaitItem()
      assertEquals(1, list.size)
      assertEquals("Cat", list[0].sourceWord)
    }

    translationDao.searchHistory("CAT").test {
      val list = awaitItem()
      assertEquals(1, list.size)
      assertEquals("Cat", list[0].sourceWord)
    }
  }

  @Test
  fun `test searchHistory partial match`() = runBlocking {
    val t1 = createTranslation(sourceWord = "Butterfly", sourceLang = "en", targetLang = "es")
    val t2 = createTranslation(sourceWord = "Cat", sourceLang = "en", targetLang = "es")
    translationDao.insertTranslation(t1)
    translationDao.insertTranslation(t2)

    translationDao.searchHistory("butter").test {
      val list = awaitItem()
      assertEquals(1, list.size)
      assertEquals("Butterfly", list[0].sourceWord)
    }
  }

  @Test
  fun `test searchHistory returns empty when no match`() = runBlocking {
    val t1 = createTranslation(sourceWord = "Cat", sourceLang = "en", targetLang = "es")
    translationDao.insertTranslation(t1)

    translationDao.searchHistory("xyz").test {
      val list = awaitItem()
      assertEquals(0, list.size)
    }
  }

  @Test
  fun `test searchHistory returns multiple matches`() = runBlocking {
    val t1 = createTranslation(sourceWord = "Cat", sourceLang = "en", targetLang = "es")
    val t2 = createTranslation(sourceWord = "Category", sourceLang = "en", targetLang = "es")
    val t3 = createTranslation(sourceWord = "Dog", sourceLang = "en", targetLang = "es")
    translationDao.insertTranslation(t1)
    translationDao.insertTranslation(t2)
    translationDao.insertTranslation(t3)

    translationDao.searchHistory("Cat").test {
      val list = awaitItem()
      assertEquals(2, list.size)
      assertTrue(list.any { it.sourceWord == "Cat" })
      assertTrue(list.any { it.sourceWord == "Category" })
    }
  }

  @Test
  fun `test searchHistory ordered by timestamp desc`() = runBlocking {
    val t1 =
        createTranslation(sourceWord = "Cat", sourceLang = "en", targetLang = "es", timestamp = 1)
    val t2 =
        createTranslation(
            sourceWord = "Category", sourceLang = "en", targetLang = "es", timestamp = 2)
    translationDao.insertTranslation(t1)
    translationDao.insertTranslation(t2)

    translationDao.searchHistory("Cat").test {
      val list = awaitItem()
      assertEquals(2, list.size)
      assertEquals("Category", list[0].sourceWord)
      assertEquals("Cat", list[1].sourceWord)
    }
  }
}
