package com.anysoftkeyboard.janus.app.ui.data

import com.anysoftkeyboard.janus.database.entities.Translation

data class UiTranslation(
    val sourceWord: String,
    val sourceLang: String,
    val sourceArticleUrl: String,
    val sourceShortDescription: String?,
    val sourceSummary: String?,
    val targetWord: String,
    val targetLang: String,
    val targetArticleUrl: String,
    val targetShortDescription: String?,
    val targetSummary: String?,
    val isFavorite: Boolean,
    val timestamp: Long,
    val id: Int,
) {
  companion object {
    fun fromTranslation(translation: Translation): UiTranslation {
      return UiTranslation(
          sourceWord = translation.sourceWord,
          sourceLang = translation.sourceLangCode,
          sourceArticleUrl = translation.sourceArticleUrl,
          sourceShortDescription = translation.sourceShortDescription,
          sourceSummary = translation.sourceSummary,
          targetWord = translation.translatedWord,
          targetLang = translation.targetLangCode,
          targetArticleUrl = translation.targetArticleUrl,
          targetShortDescription = translation.targetShortDescription,
          targetSummary = translation.targetSummary,
          isFavorite = translation.isFavorite,
          timestamp = translation.timestamp,
          id = translation.id)
    }
  }
}
