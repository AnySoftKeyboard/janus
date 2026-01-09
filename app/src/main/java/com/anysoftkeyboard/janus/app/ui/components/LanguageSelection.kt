package com.anysoftkeyboard.janus.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.anysoftkeyboard.janus.app.util.supportedLanguages
import com.anysoftkeyboard.janus.app.util.supportedLanguagesMap
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewState
import com.anysoftkeyboard.janus.app.viewmodels.TranslationState

/**
 * Language selection row with source/target selectors and swap button.
 *
 * Handles:
 * - Source and target language selection
 * - Swapping languages with icon button
 * - Auto-filling search term with translated word when swapping
 *
 * @param sourceLang Currently selected source language
 * @param targetLang Currently selected target language
 * @param pageState Current translation state for extracting translated word
 * @param recentLanguages List of recently used language codes
 * @param onSourceLanguageSelected Callback when source language is selected
 * @param onTargetLanguageSelected Callback when target language is selected
 * @param onSwapLanguages Callback when swap button is clicked with new search term
 */
@Composable
fun LanguageSelectionRow(
    sourceLang: String,
    targetLang: String,
    pageState: TranslateViewState,
    recentLanguages: List<String>,
    onSourceLanguageSelected: (String) -> Unit,
    onTargetLanguageSelected: (String) -> Unit,
    onSwapLanguages: (String, String, String) -> Unit,
) {
  Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
  ) {
    LanguageSelector(
        selectedLanguage = sourceLang,
        recentLanguages = recentLanguages,
        onLanguageSelected = onSourceLanguageSelected,
        modifier = Modifier.testTag("source_lang_selector"),
    )
    IconButton(
        onClick = {
          // If translation is shown, put target word in input, else clear
          val newSearchTerm =
              if (pageState is TranslateViewState.Translated) {
                when (val translationState = pageState.translation) {
                  is TranslationState.Translated -> translationState.translation.translatedWord
                  else -> ""
                }
              } else {
                ""
              }
          onSwapLanguages(targetLang, sourceLang, newSearchTerm)
        }
    ) {
      Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = "Swap languages",
      )
    }
    LanguageSelector(
        selectedLanguage = targetLang,
        recentLanguages = recentLanguages,
        onLanguageSelected = onTargetLanguageSelected,
        modifier = Modifier.testTag("target_lang_selector"),
    )
  }
}

/**
 * Dropdown selector for a single language.
 *
 * @param selectedLanguage Currently selected language code
 * @param recentLanguages List of recently used language codes
 * @param onLanguageSelected Callback when a language is selected
 */
@Composable
fun LanguageSelector(
    selectedLanguage: String,
    recentLanguages: List<String>,
    onLanguageSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
  // In a real app, you'd get this from a ViewModel
  val languages = supportedLanguages
  val languagesMap = supportedLanguagesMap
  var expanded by remember { mutableStateOf(false) }

  // Find name for selected code
  // Bolt optimization: Use map for O(1) lookup instead of O(N) list traversal
  val selectedName = supportedLanguagesMap[selectedLanguage]?.name ?: selectedLanguage

  Box(modifier = modifier) {
    Button(onClick = { expanded = true }) { Text(selectedName) }

    if (expanded) {
      SupportedLanguagePickerDialog(
          languages = languages,
          recentLanguages = recentLanguages,
          onLanguageSelected = onLanguageSelected,
          onDismiss = { expanded = false },
      )
    }
  }
}
