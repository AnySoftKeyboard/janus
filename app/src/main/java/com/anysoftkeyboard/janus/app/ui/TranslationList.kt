package com.anysoftkeyboard.janus.app.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation

@Composable
fun TranslationList(translations: List<UiTranslation>) {
  LazyColumn { items(translations) { translation -> TranslationCard(translation) } }
}
