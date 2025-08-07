package com.anysoftkeyboard.janus.network

import com.anysoftkeyboard.janus.database.TranslationDao
import com.anysoftkeyboard.janus.database.Translation

class TranslationRepository(private val wikipediaApi: WikipediaApi, private val translationDao: TranslationDao) {

    suspend fun getTranslation(sourceWord: String, sourceLang: String, targetLang: String): Translation? {
        // First, try to get the translation from the database
        val cachedTranslation = translationDao.findTranslation(sourceWord, sourceLang, targetLang)
        if (cachedTranslation != null) {
            return cachedTranslation
        }

        // If not found in the database, fetch from the network
        return try {
            val searchResponse = wikipediaApi.search(sourceWord)
            val searchResult = searchResponse.query.search.firstOrNull()

            if (searchResult != null) {
                val newTranslation = Translation(
                    sourceWord = sourceWord,
                    sourceLangCode = sourceLang,
                    sourceArticleUrl = "https://en.wikipedia.org/wiki/${searchResult.title}",
                    sourceShortDescription = searchResult.snippet,
                    sourceSummary = null, // Wikipedia search API does not provide full summary
                    translatedWord = searchResult.title, // For now, assume translated word is the article title
                    targetLangCode = targetLang,
                    targetArticleUrl = "https://en.wikipedia.org/wiki/${searchResult.title}",
                    targetShortDescription = searchResult.snippet,
                    targetSummary = null
                )
                translationDao.insertTranslation(newTranslation)
                newTranslation
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}