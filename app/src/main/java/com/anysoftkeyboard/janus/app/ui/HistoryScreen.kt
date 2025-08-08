package com.anysoftkeyboard.janus.app.ui

import androidx.compose.runtime.Composable
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation

@Composable
fun HistoryScreen() {
  // In a real app, you'd get this from a ViewModel
  val history =
      listOf(
          UiTranslation("Hello", "English", "Hola", "Spanish", "A common greeting.", false),
          UiTranslation("World", "English", "Mundo", "Spanish", "The planet we live on.", true),
      )
  TranslationList(translations = history)
}
