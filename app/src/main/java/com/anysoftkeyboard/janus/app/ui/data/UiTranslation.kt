package com.anysoftkeyboard.janus.app.ui.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector
import com.anysoftkeyboard.janus.database.entities.Translation

data class UiTranslation(
    val sourceWord: String,
    val sourceLang: String,
    val targetWord: String,
    val targetLang: String,
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
          targetWord = translation.translatedWord,
          targetLang = translation.targetLangCode,
          shortDescription = translation.sourceShortDescription,
          isFavorite = translation.isFavorite)
    }
  }
}
