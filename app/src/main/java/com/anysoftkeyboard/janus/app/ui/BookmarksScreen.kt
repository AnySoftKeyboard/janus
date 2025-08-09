package com.anysoftkeyboard.janus.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import com.anysoftkeyboard.janus.app.viewmodels.BookmarksViewModel

@Composable
fun BookmarksScreen(viewModel: BookmarksViewModel) {
  val bookmarks by viewModel.bookmarks.collectAsState()
  TranslationList(translations = bookmarks.map { UiTranslation.fromTranslation(it) })
}
