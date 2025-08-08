package com.anysoftkeyboard.janus.app.ui

import androidx.compose.runtime.Composable
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation

@Composable
fun BookmarksScreen() {
  // In a real app, you'd get this from a ViewModel
  val bookmarks =
      listOf(
          UiTranslation("World", "English", "Mundo", "Spanish", "The planet we live on.", true),
      )
  TranslationList(translations = bookmarks)
}
