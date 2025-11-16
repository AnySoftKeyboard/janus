package com.anysoftkeyboard.janus.app.ui

import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.components.LanguageSelectionRow
import com.anysoftkeyboard.janus.app.ui.components.SearchInputField
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import com.anysoftkeyboard.janus.app.ui.states.ErrorStateDisplay
import com.anysoftkeyboard.janus.app.ui.states.InitialEmptyState
import com.anysoftkeyboard.janus.app.ui.states.LoadingState
import com.anysoftkeyboard.janus.app.ui.states.SearchResultsView
import com.anysoftkeyboard.janus.app.ui.states.TranslationView
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewModel
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewState

/**
 * Main translation screen.
 *
 * Orchestrates the translation workflow:
 * 1. Language selection (source/target with swap)
 * 2. Search input
 * 3. State-based content display (empty, loading, results, translation, error)
 *
 * @param viewModel ViewModel managing translation state
 */
@Composable
fun TranslateScreen(viewModel: TranslateViewModel) {
  var text by remember { mutableStateOf("") }
  var sourceLang by remember { mutableStateOf("en") }
  var targetLang by remember { mutableStateOf("he") }
  val pageState by viewModel.pageState.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }

  // Handle back button navigation within translation flow
  BackHandler(enabled = pageState !is TranslateViewState.Empty) {
    when (pageState) {
      is TranslateViewState.Translated -> viewModel.backToSearchResults()
      is TranslateViewState.OptionsFetched -> viewModel.clearSearch()
      is TranslateViewState.Error -> viewModel.clearSearch()
      is TranslateViewState.FetchingOptions -> {
        // Let system handle back during loading
      }
      else -> {
        // Empty state - let system back work normally
      }
    }
  }

  Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally) {
          // Language selection with swap button
          LanguageSelectionRow(
              sourceLang = sourceLang,
              targetLang = targetLang,
              pageState = pageState,
              onSourceLanguageSelected = { sourceLang = it },
              onTargetLanguageSelected = { targetLang = it },
              onSwapLanguages = { newSource, newTarget, newSearchTerm ->
                sourceLang = newSource
                targetLang = newTarget
                text = newSearchTerm
                viewModel.searchArticles("", "")
              })

          Spacer(modifier = Modifier.height(16.dp))

          // Search input field
          SearchInputField(
              text = text,
              onTextChange = { text = it },
              onSearch = { viewModel.searchArticles(sourceLang, text) })

          Spacer(modifier = Modifier.height(16.dp))

          // State-based content display
          when (pageState) {
            is TranslateViewState.Empty -> InitialEmptyState()
            is TranslateViewState.FetchingOptions -> LoadingState()
            is TranslateViewState.OptionsFetched ->
                SearchResultsView(
                    pageState = pageState as TranslateViewState.OptionsFetched,
                    viewModel = viewModel,
                    sourceLang = sourceLang,
                    targetLang = targetLang,
                    snackbarHostState = snackbarHostState)
            is TranslateViewState.Translated ->
                TranslationView(translated = pageState as TranslateViewState.Translated)
            is TranslateViewState.Error ->
                ErrorStateDisplay(
                    error = pageState as TranslateViewState.Error,
                    snackbarHostState = snackbarHostState)
          }
        }
  }
}

/**
 * Card displaying a saved translation (used in History and Bookmarks).
 *
 * @param translation The translation data to display
 */
@Composable
fun TranslationCard(translation: UiTranslation) {
  Card(modifier = Modifier.fillMaxWidth()) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = translation.sourceWord, style = MaterialTheme.typography.headlineSmall)
      Text(
          text = stringResource(R.string.translation_source_from, translation.sourceLang),
          style = MaterialTheme.typography.bodySmall)
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = translation.targetWord, style = MaterialTheme.typography.headlineMedium)
      Text(
          text = stringResource(R.string.translation_target_in, translation.targetLang),
          style = MaterialTheme.typography.bodySmall)
      Spacer(modifier = Modifier.height(8.dp))
      AndroidView(
          factory = { context ->
            TextView(context).apply { movementMethod = LinkMovementMethod.getInstance() }
          },
          update = { setHtmlToText(it, translation.shortDescription ?: "") })
      IconButton(onClick = { /* TODO */ }) {
        Icon(imageVector = translation.favoriteIcon, contentDescription = "Favorite")
      }
    }
  }
}

/** Helper function to set HTML content to TextView with proper API level handling. */
private fun setHtmlToText(view: TextView, snippet: String) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    view.text = Html.fromHtml(snippet, Html.FROM_HTML_MODE_COMPACT)
  } else {
    @Suppress("DEPRECATION") view.text = Html.fromHtml(snippet)
  }
}
