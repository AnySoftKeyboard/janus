package com.anysoftkeyboard.janus.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewState
import com.anysoftkeyboard.janus.app.viewmodels.TranslationState
import com.anysoftkeyboard.janus.app.util.supportedLanguages

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
 * @param onSourceLanguageSelected Callback when source language is selected
 * @param onTargetLanguageSelected Callback when target language is selected
 * @param onSwapLanguages Callback when swap button is clicked with new search term
 */
@Composable
fun LanguageSelectionRow(
    sourceLang: String,
    targetLang: String,
    pageState: TranslateViewState,
    onSourceLanguageSelected: (String) -> Unit,
    onTargetLanguageSelected: (String) -> Unit,
    onSwapLanguages: (String, String, String) -> Unit
) {
  Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically) {
        LanguageSelector(
            selectedLanguage = sourceLang, onLanguageSelected = onSourceLanguageSelected)
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
            }) {
              Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                  contentDescription = "Swap languages")
            }
        LanguageSelector(
            selectedLanguage = targetLang, onLanguageSelected = onTargetLanguageSelected)
      }
}

/**
 * Dropdown selector for a single language.
 *
 * @param selectedLanguage Currently selected language code
 * @param onLanguageSelected Callback when a language is selected
 */
@Composable
fun LanguageSelector(selectedLanguage: String, onLanguageSelected: (String) -> Unit) {
  // In a real app, you'd get this from a ViewModel
  val languages = supportedLanguages
  var expanded by remember { mutableStateOf(false) }

  // Find name for selected code
  val selectedName = languages.find { it.code == selectedLanguage }?.name ?: selectedLanguage

  Box {
    Button(onClick = { expanded = true }) { Text(selectedName) }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
      languages.forEach { language ->
        DropdownMenuItem(
            text = { Text(language.name) },
            onClick = {
              onLanguageSelected(language.code)
              expanded = false
            })
      }
    }
  }
}
