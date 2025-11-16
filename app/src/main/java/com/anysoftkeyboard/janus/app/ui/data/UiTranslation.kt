package com.anysoftkeyboard.janus.app.ui.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector
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
  val favoriteIcon: ImageVector
    get() = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder

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
