package com.anysoftkeyboard.janus.app.ui.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector

data class UiTranslation(
    val sourceWord: String,
    val sourceLang: String,
    val targetWord: String,
    val targetLang: String,
    val shortDescription: String,
    val isFavorite: Boolean,
) {
  val favoriteIcon: ImageVector
    get() = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
}
