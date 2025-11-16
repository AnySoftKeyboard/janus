package com.anysoftkeyboard.janus.app.ui.data

import com.anysoftkeyboard.janus.database.entities.Translation

data class UiTranslation(
    val sourceWord: String,
    val sourceLang: String,
    val sourceArticleUrl: String,
    val targetWord: String,
    val targetLang: String,
    val targetArticleUrl: String,
    val shortDescription: String?,
    val isFavorite: Boolean,
) {
  companion object {
    fun fromTranslation(translation: Translation): UiTranslation {
      return UiTranslation(
          sourceWord = translation.sourceWord,
          sourceLang = translation.sourceLangCode,
          sourceArticleUrl = translation.sourceArticleUrl,
          targetWord = translation.translatedWord,
          targetLang = translation.targetLangCode,
          targetArticleUrl = translation.targetArticleUrl,
          shortDescription = translation.sourceShortDescription,
          isFavorite = translation.isFavorite)
    }
  }
}
