package com.anysoftkeyboard.janus.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.util.supportedLanguagesMap

@Composable
fun DisambiguationDialog(
    candidates: List<String>,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
  AlertDialog(
      onDismissRequest = onDismiss,
      title = { Text(text = stringResource(R.string.disambiguation_title)) },
      text = {
        Column {
          Text(text = stringResource(R.string.disambiguation_message))
          Spacer(modifier = Modifier.height(16.dp))
          LazyColumn {
            items(candidates) { langCode ->
              val languageName = supportedLanguagesMap[langCode]?.name ?: langCode.uppercase()
              Row(
                  modifier =
                      Modifier.fillMaxWidth()
                          .clickable { onLanguageSelected(langCode) }
                          .padding(vertical = 12.dp, horizontal = 4.dp),
                  verticalAlignment = Alignment.CenterVertically,
              ) {
                Text(
                    text = languageName,
                    style = MaterialTheme.typography.bodyLarge,
                )
              }
            }
          }
        }
      },
      confirmButton = {},
      dismissButton = {
        TextButton(onClick = onDismiss) { Text(stringResource(android.R.string.cancel)) }
      },
  )
}
