package com.anysoftkeyboard.janus.app.ui

import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import com.anysoftkeyboard.janus.app.repository.OptionalSourceTerm
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewModel
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewState
import com.anysoftkeyboard.janus.app.viewmodels.TranslationState

private fun setHtmlToText(view: TextView, snippet: String) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    view.text = Html.fromHtml(snippet, Html.FROM_HTML_MODE_COMPACT)
  } else {
    @Suppress("DEPRECATION")
    view.text = Html.fromHtml(snippet)
  }
}

@Composable
fun TranslateScreen(viewModel: TranslateViewModel) {
  var text by remember { mutableStateOf("") }
  var sourceLang by remember { mutableStateOf("en") }
  var targetLang by remember { mutableStateOf("he") }
  val pageState by viewModel.pageState.collectAsState()

  Column(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
              LanguageSelector(
                  selectedLanguage = sourceLang, onLanguageSelected = { sourceLang = it })
              Spacer(modifier = Modifier.width(8.dp))
              OutlinedTextField(
                  value = text,
                  onValueChange = { text = it },
                  label = { Text("Word to translate") },
                  modifier = Modifier.fillMaxWidth())
            }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
              Button(onClick = { viewModel.searchArticles(sourceLang, text) }) { Text("Search") }
              Spacer(modifier = Modifier.width(8.dp))
              LanguageSelector(
                  selectedLanguage = targetLang, onLanguageSelected = { targetLang = it })
            }
        Spacer(modifier = Modifier.height(16.dp))

        when (pageState) {
          is TranslateViewState.Empty -> Text(text = "Welcome. What do you want to translate?")
          is TranslateViewState.FetchingOptions -> CircularProgressIndicator()
          is TranslateViewState.OptionsFetched -> {
            val pageState = (pageState as TranslateViewState.OptionsFetched)
            AvailableSourceArticles(pageState, viewModel, sourceLang, targetLang)
          }
          is TranslateViewState.Translated -> {
            val translated = (pageState as TranslateViewState.Translated)
            ShowTranslatedArticle(translated)
          }
          is TranslateViewState.Error -> {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(24.dp))
          }
        }
      }
}

@Composable
fun ShowTranslatedArticle(translated: TranslateViewState.Translated) {
  Text(
      "${translated.term.title} (${translated.sourceLang})",
      style = MaterialTheme.typography.headlineSmall)
  when (translated.translation) {
    is TranslationState.Translated -> {
      val translated = translated.translation.translation
      Text(
          "${translated.translatedWord} (${translated.targetLangCode})",
          style = MaterialTheme.typography.headlineSmall)
      Text(
          "${translated.targetShortDescription ?: translated.targetSummary ?: "No description"} (${translated.targetLangCode})",
          style = MaterialTheme.typography.bodySmall)
    }

    is TranslationState.MissingTranslation -> {
      val availableTranslations =
          translated.translation.availableTranslations.joinToString(", ") { it.targetLangCode }
      Text(
          "Could not find a translation for ${translated.targetLang}. But we have for ${availableTranslations}")
    }

    else -> {
      Text(
          "${translated.term.title} (${translated.sourceLang}) -> state type ${translated.translation.javaClass}")
    }
  }
}

@Composable
fun AvailableSourceArticles(
    pageState: TranslateViewState.OptionsFetched,
    viewModel: TranslateViewModel,
    sourceLang: String,
    targetLang: String
) {
  val translatedArticles = pageState.options.filter { targetLang in it.availableLanguages }
  val untranslatedArticles = pageState.options.filter { targetLang !in it.availableLanguages }

  LazyColumn {
    items(translatedArticles) { item ->
      SearchResultItem(
          result = item,
          targetLang = targetLang,
          showAvailableLanguages = false,
          isLoading = pageState.translations[item] is TranslationState.Translating,
          isError = pageState.translations[item] is TranslationState.Error,
          onClick = { viewModel.fetchTranslation(pageState, item, sourceLang, targetLang) })
    }

    if (translatedArticles.isNotEmpty() && untranslatedArticles.isNotEmpty()) {
      item {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center) {
              Text(
                  text = "──── Untranslated Articles ────",
                  style = MaterialTheme.typography.labelMedium,
                  color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
      }
    }

    items(untranslatedArticles) { item ->
      SearchResultItem(
          result = item,
          targetLang = targetLang,
          showAvailableLanguages = true,
          isLoading = pageState.translations[item] is TranslationState.Translating,
          isError = pageState.translations[item] is TranslationState.Error,
          onClick = { viewModel.fetchTranslation(pageState, item, sourceLang, targetLang) })
    }
  }
}

@Composable
fun SearchResultItem(
    result: OptionalSourceTerm,
    targetLang: String,
    showAvailableLanguages: Boolean,
    isLoading: Boolean,
    isError: Boolean,
    onClick: () -> Unit
) {
  Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable(onClick = onClick)) {
    Row(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 0.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
          Text(text = result.title, style = MaterialTheme.typography.headlineSmall)
        }
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
          if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
          } else if (isError) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(24.dp))
          } else if (showAvailableLanguages) {
            Text(
                text = "Available: ${result.availableLanguages.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
          } else {
            AndroidView(
                factory = { context ->
                  TextView(context).apply { movementMethod = LinkMovementMethod.getInstance() }
                },
                update = { setHtmlToText(it, result.snippet) })
          }
        }
  }
}

@Composable
fun LanguageSelector(selectedLanguage: String, onLanguageSelected: (String) -> Unit) {
  // In a real app, you'd get this from a ViewModel
  val languages = listOf("en", "he", "fr", "de")
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
          update = { setHtmlToText(it, translation.shortDescription ?: "") })
      IconButton(onClick = { /* TODO */ }) {
        Icon(imageVector = translation.favoriteIcon, contentDescription = "Favorite")
      }
    }
  }
}
