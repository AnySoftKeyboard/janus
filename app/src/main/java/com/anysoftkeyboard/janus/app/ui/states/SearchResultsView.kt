package com.anysoftkeyboard.janus.app.ui.states

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.repository.OptionalSourceTerm
import com.anysoftkeyboard.janus.app.ui.components.LanguagePickerDialog
import com.anysoftkeyboard.janus.app.ui.items.SearchResultItem
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewModel
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewState
import com.anysoftkeyboard.janus.app.viewmodels.TranslationState

/**
 * Displays search results with options to fetch translations.
 *
 * Shows two sections:
 * 1. Articles with translations available in target language
 * 2. Articles without translations (untranslated articles)
 *
 * Handles error snackbar notifications for failed translation attempts.
 *
 * @param pageState State containing search results and translation statuses
 * @param viewModel ViewModel for triggering translation fetches
 * @param sourceLang Source language code
 * @param targetLang Target language code
 * @param snackbarHostState State for showing error notifications
 */
@Composable
fun SearchResultsView(
    pageState: TranslateViewState.OptionsFetched,
    viewModel: TranslateViewModel,
    sourceLang: String,
    targetLang: String,
    snackbarHostState: SnackbarHostState,
    instruction: String
) {
  val translatedArticles = pageState.options.filter { targetLang in it.availableLanguages }
  val untranslatedArticles = pageState.options.filter { targetLang !in it.availableLanguages }

  // State for language picker dialog
  var showLanguagePickerDialog by remember { mutableStateOf(false) }
  var selectedArticleForPicker by remember { mutableStateOf<OptionalSourceTerm?>(null) }

  // Show snackbar immediately when translation errors occur
  LaunchedEffect(pageState.translations) {
    pageState.translations.values.forEach { translationState ->
      if (translationState is TranslationState.Error) {
        snackbarHostState.showSnackbar(translationState.errorMessage)
      }
    }
  }

  // Show empty state if no results found
  if (pageState.options.isEmpty()) {
    NoResultsState(searchTerm = pageState.searchTerm)
    return
  }

  LazyColumn {
    item {
      Row(
          modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = instruction,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
    }
    // Translated articles section
    items(translatedArticles) { item ->
      val translationState = pageState.translations[item]
      val errorMessage =
          if (translationState is TranslationState.Error) translationState.errorMessage else null

      SearchResultItem(
          result = item,
          sourceLang = sourceLang,
          targetLang = targetLang,
          showAvailableLanguages = false,
          isLoading = translationState is TranslationState.Translating,
          errorMessage = errorMessage,
          onClick =
              if (errorMessage != null) {
                // No onClick handler for articles with errors
                null
              } else {
                { viewModel.fetchTranslation(pageState, item, sourceLang, targetLang) }
              })
    }

    // Divider between sections
    if (translatedArticles.isNotEmpty() && untranslatedArticles.isNotEmpty()) {
      item { SectionDivider() }
    }

    // Untranslated articles section
    items(untranslatedArticles) { item ->
      val translationState = pageState.translations[item]
      val errorMessage =
          if (translationState is TranslationState.Error) translationState.errorMessage else null

      SearchResultItem(
          result = item,
          sourceLang = sourceLang,
          targetLang = targetLang,
          showAvailableLanguages = true,
          isLoading = translationState is TranslationState.Translating,
          errorMessage = errorMessage,
          onClick =
              if (item.availableLanguages.isEmpty() || errorMessage != null) {
                // No onClick handler for articles with no translations or errors
                null
              } else {
                {
                  // Show language picker dialog for untranslated articles
                  selectedArticleForPicker = item
                  showLanguagePickerDialog = true
                }
              })
    }
  }

  // Language picker dialog for untranslated articles
  if (showLanguagePickerDialog && selectedArticleForPicker != null) {
    LanguagePickerDialog(
        availableLanguages = selectedArticleForPicker!!.availableLanguages,
        onLanguageSelected = { selectedLanguage ->
          // Fetch translation with the selected language
          viewModel.fetchTranslation(
              pageState, selectedArticleForPicker!!, sourceLang, selectedLanguage)
          showLanguagePickerDialog = false
          selectedArticleForPicker = null
        },
        onDismiss = {
          showLanguagePickerDialog = false
          selectedArticleForPicker = null
        })
  }
}

/** Divider between translated and untranslated articles sections. */
@Composable
private fun SectionDivider() {
  Column(
      modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.3f),
            color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.search_results_untranslated_section).uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(0.3f),
            color = MaterialTheme.colorScheme.outlineVariant)
      }
}
