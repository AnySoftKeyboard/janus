package com.anysoftkeyboard.janus.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import com.anysoftkeyboard.janus.app.viewmodels.HistoryViewModel

@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
  val history by viewModel.history.collectAsState()
  TranslationList(translations = history.map { UiTranslation.fromTranslation(it) })
}
