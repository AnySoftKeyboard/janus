package com.anysoftkeyboard.janus.app.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.components.JanusLoader
import com.anysoftkeyboard.janus.app.ui.components.LanguageSelectionRow
import com.anysoftkeyboard.janus.app.ui.components.SearchInputField
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
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TranslateScreen(viewModel: TranslateViewModel) {
  var text by remember { mutableStateOf("") }
  var sourceLang by remember { mutableStateOf("en") }
  var targetLang by remember { mutableStateOf("he") }
  val pageState by viewModel.pageState.collectAsState()
  val welcomeMessage by viewModel.welcomeMessage.collectAsState()
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
    SharedTransitionLayout {
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
                  if (newSearchTerm.isNotEmpty()) {
                    viewModel.searchArticles(newSource, newSearchTerm)
                  } else {
                    viewModel.clearSearch()
                  }
                })

            Spacer(modifier = Modifier.height(16.dp))

            // Search input field
            SearchInputField(
                text = text,
                onTextChange = { text = it },
                onSearch = { viewModel.searchArticles(sourceLang, text) })

            Spacer(modifier = Modifier.height(16.dp))

            // State-based content display
            AnimatedContent(targetState = pageState, label = "TranslateScreenStateTransition") {
                targetState ->
              when (targetState) {
                is TranslateViewState.Empty ->
                    InitialEmptyState(
                        stringResource(welcomeMessage.welcomeMessageResId),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this)
                is TranslateViewState.FetchingOptions ->
                    LoadingState(
                        stringResource(welcomeMessage.loadingMessageResId),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this)
                is TranslateViewState.OptionsFetched ->
                    SearchResultsView(
                        pageState = targetState as TranslateViewState.OptionsFetched,
                        viewModel = viewModel,
                        sourceLang = sourceLang,
                        targetLang = targetLang,
                        snackbarHostState = snackbarHostState,
                        instruction =
                            stringResource(
                                welcomeMessage.searchInstructionResId, targetLang.uppercase()),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this)
                is TranslateViewState.Translating ->
                    Row(
                        modifier =
                            Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                          JanusLoader(
                              modifier =
                                  Modifier.size(48.dp)
                                      .sharedElement(
                                          sharedContentState =
                                              rememberSharedContentState(key = "shared_icon"),
                                          animatedVisibilityScope = this@AnimatedContent))
                          Spacer(modifier = Modifier.width(8.dp))
                          Text(
                              text =
                                  stringResource(
                                      R.string.translating_loading_message, targetLang.uppercase()),
                              style = MaterialTheme.typography.labelMedium,
                              color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                is TranslateViewState.Translated ->
                    TranslationView(
                        translated = targetState as TranslateViewState.Translated,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this)
                is TranslateViewState.Error ->
                    ErrorStateDisplay(
                        error = targetState as TranslateViewState.Error,
                        snackbarHostState = snackbarHostState)
              }
            }
          }
    }
  }
}
