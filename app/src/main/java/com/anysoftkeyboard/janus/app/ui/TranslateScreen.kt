package com.anysoftkeyboard.janus.app.ui

import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewModel

@Composable
fun TranslateScreen(viewModel: TranslateViewModel) {
  var text by remember { mutableStateOf("") }
  var sourceLang by remember { mutableStateOf("English") }
  var targetLang by remember { mutableStateOf("Spanish") }
  val translations by viewModel.translations.collectAsState()

  Column(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Word to translate") },
            modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
              LanguageSelector(
                  selectedLanguage = sourceLang, onLanguageSelected = { sourceLang = it })
              Text("to")
              LanguageSelector(
                  selectedLanguage = targetLang, onLanguageSelected = { targetLang = it })
            }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.search(sourceLang, text) }) { Text("Translate") }
        Spacer(modifier = Modifier.height(16.dp))
        TranslationList(translations.map { UiTranslation.fromTranslation(it) })
      }
}

@Composable
fun LanguageSelector(selectedLanguage: String, onLanguageSelected: (String) -> Unit) {
  // In a real app, you'd get this from a ViewModel
  val languages = listOf("English", "Spanish", "French", "German")
  var expanded by remember { mutableStateOf(false) }

  Box {
    Button(onClick = { expanded = true }) { Text(selectedLanguage) }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
      languages.forEach { language ->
        DropdownMenuItem(
            text = { Text(language) },
            onClick = {
              onLanguageSelected(language)
              expanded = false
            })
      }
    }
  }
}

@Composable
fun TranslationCard(translation: UiTranslation) {
  Card(modifier = Modifier.fillMaxWidth()) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = translation.sourceWord, style = MaterialTheme.typography.headlineSmall)
      Text(text = "from ${translation.sourceLang}", style = MaterialTheme.typography.bodySmall)
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = translation.targetWord, style = MaterialTheme.typography.headlineMedium)
      Text(text = "in ${translation.targetLang}", style = MaterialTheme.typography.bodySmall)
      Spacer(modifier = Modifier.height(8.dp))
      AndroidView(
          factory = { context ->
            TextView(context).apply { movementMethod = LinkMovementMethod.getInstance() }
          },
          update = {
            it.text = Html.fromHtml(translation.shortDescription, Html.FROM_HTML_MODE_COMPACT)
          })
      IconButton(onClick = { /* TODO */ }) {
        Icon(imageVector = translation.favoriteIcon, contentDescription = "Favorite")
      }
    }
  }
}
