package com.anysoftkeyboard.janus.network

import com.anysoftkeyboard.janus.database.Translation
import com.anysoftkeyboard.janus.database.TranslationDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

class TranslationRepositoryTest {

    private lateinit var wikipediaApi: WikipediaApi
    private lateinit var translationDao: TranslationDao
    private lateinit var repository: TranslationRepository

    @Before
    fun setup() {
        wikipediaApi = mockk()
        translationDao = mockk()
        repository = TranslationRepository(wikipediaApi, translationDao)
    }

    @Test
    fun `getTranslation_returnsCachedTranslationWhenAvailable`() = runTest {
        val sourceWord = "hello"
        val sourceLang = "en"
        val targetLang = "es"
        val cachedTranslation = Translation(
            id = 1,
            sourceWord = sourceWord,
            sourceLangCode = sourceLang,
            sourceArticleUrl = "url_cached",
            sourceShortDescription = "desc_cached",
            sourceSummary = null,
            translatedWord = "hola",
            targetLangCode = targetLang,
            targetArticleUrl = "url_cached",
            targetShortDescription = "desc_cached",
            targetSummary = null
        )

        coEvery { translationDao.findTranslation(sourceWord, sourceLang, targetLang) } returns cachedTranslation

        val result = repository.getTranslation(sourceWord, sourceLang, targetLang)

        assertEquals(cachedTranslation, result)
        coVerify(exactly = 0) { wikipediaApi.search(any()) }
        coVerify(exactly = 0) { translationDao.insertTranslation(any()) }
    }

    @Test
    fun `getTranslation_fetchesFromNetworkAndCachesWhenNotAvailable`() = runTest {
        val sourceWord = "hello"
        val sourceLang = "en"
        val targetLang = "es"
        val networkResponse = SearchResponse(
            query = Query(
                search = listOf(
                    SearchResult("Hola", "Spanish for hello")
                )
            )
        )

        coEvery { translationDao.findTranslation(sourceWord, sourceLang, targetLang) } returns null
        coEvery { wikipediaApi.search(sourceWord) } returns networkResponse
        coEvery { translationDao.insertTranslation(any()) } returns Unit

        val result = repository.getTranslation(sourceWord, sourceLang, targetLang)

        assertEquals("Hola", result?.translatedWord)
        coVerify(exactly = 1) { wikipediaApi.search(sourceWord) }
        coVerify(exactly = 1) { translationDao.insertTranslation(any()) }
    }

    @Test
    fun `getTranslation_returnsNullWhenNetworkFailsAndNoCache`() = runTest {
        val sourceWord = "hello"
        val sourceLang = "en"
        val targetLang = "es"

        coEvery { translationDao.findTranslation(sourceWord, sourceLang, targetLang) } returns null
        coEvery { wikipediaApi.search(sourceWord) } throws RuntimeException("Network error")

        val result = repository.getTranslation(sourceWord, sourceLang, targetLang)

        assertNull(result)
        coVerify(exactly = 1) { wikipediaApi.search(sourceWord) }
        coVerify(exactly = 0) { translationDao.insertTranslation(any()) }
    }
}
