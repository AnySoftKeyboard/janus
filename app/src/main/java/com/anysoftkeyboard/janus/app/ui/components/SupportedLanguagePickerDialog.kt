package com.anysoftkeyboard.janus.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.TranslationHeader
import com.anysoftkeyboard.janus.app.util.SupportedLanguage
import com.anysoftkeyboard.janus.app.util.supportedLanguagesMap

/**
 * Dialog for selecting a language from the list of supported languages.
 *
 * Uses [LazyColumn] to efficiently render the large list of languages (300+ items), avoiding the
 * performance overhead of [androidx.compose.material3.DropdownMenu] which composes all items
 * eagerly.
 *
 * @param languages List of all supported languages
 * @param recentLanguages List of recently used language codes
 * @param onLanguageSelected Callback when a language is selected
 * @param onDismiss Callback when dialog is dismissed
 */
@Composable
fun SupportedLanguagePickerDialog(
    languages: List<SupportedLanguage>,
    recentLanguages: List<String>,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
  val recentSupported =
      remember(recentLanguages) {
        recentLanguages.mapNotNull { code -> supportedLanguagesMap[code] }
      }

  AlertDialog(
      onDismissRequest = onDismiss,
      title = {
        Text(
            text = stringResource(R.string.language_picker_title),
            style = MaterialTheme.typography.titleLarge,
        )
      },
      text = {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().height(400.dp) // Limit height
        ) {
          if (recentSupported.isNotEmpty()) {
            item { TranslationHeader(stringResource(R.string.language_selector_recent)) }
            items(recentSupported) { language ->
              LanguageItem(language, onLanguageSelected, onDismiss)
            }
            item { TranslationHeader(stringResource(R.string.language_selector_all_languages)) }
          }

          items(languages) { language -> LanguageItem(language, onLanguageSelected, onDismiss) }
        }
      },
      confirmButton = {},
      dismissButton = {
        TextButton(onClick = onDismiss) { Text(stringResource(R.string.button_cancel)) }
      },
  )
}

@Composable
private fun LanguageItem(
    language: SupportedLanguage,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
  Column {
    Row(
        modifier =
            Modifier.fillMaxWidth()
                .clickable {
                  onLanguageSelected(language.code)
                  onDismiss()
                }
                .padding(vertical = 12.dp, horizontal = 8.dp)
                // Maintain testability with the same tag as the original DropdownMenuItem
                .testTag("language_menu_item_${language.code}"),
        verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
          text = language.name,
          style = MaterialTheme.typography.bodyLarge,
          modifier = Modifier.weight(1f),
      )
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
  }
}
