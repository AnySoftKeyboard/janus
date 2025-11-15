package com.anysoftkeyboard.janus.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Dialog for selecting a language from a list of available translations.
 *
 * Displays a scrollable list of language codes that the user can select from. Used when clicking on
 * an untranslated article to choose which available translation to view.
 *
 * @param availableLanguages List of language codes available for translation
 * @param onLanguageSelected Callback when a language is selected
 * @param onDismiss Callback when dialog is dismissed without selection
 */
@Composable
fun LanguagePickerDialog(
    availableLanguages: List<String>,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
  AlertDialog(
      onDismissRequest = onDismiss,
      title = {
        Text(text = "Select Translation Language", style = MaterialTheme.typography.titleLarge)
      },
      text = {
        LazyColumn {
          items(availableLanguages) { language ->
            Column {
              Text(
                  text = language.uppercase(),
                  style = MaterialTheme.typography.bodyLarge,
                  modifier =
                      Modifier.fillMaxWidth()
                          .clickable {
                            onLanguageSelected(language)
                            onDismiss()
                          }
                          .padding(vertical = 12.dp, horizontal = 8.dp))
              if (language != availableLanguages.last()) {
                HorizontalDivider()
              }
            }
          }
        }
      },
      confirmButton = {},
      dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } })
}
