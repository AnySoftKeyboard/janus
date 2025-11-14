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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.anysoftkeyboard.janus.app.repository.OptionalSourceTerm
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewModel
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewState
import com.anysoftkeyboard.janus.app.viewmodels.TranslationState
import kotlinx.coroutines.launch

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
  val snackbarHostState = remember { SnackbarHostState() }

  Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally) {
          // Language selectors with swap button
          Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically) {
                LanguageSelector(
                    selectedLanguage = sourceLang, onLanguageSelected = { sourceLang = it })
                IconButton(
                    onClick = {
                      // Swap languages
                      val temp = sourceLang
                      sourceLang = targetLang
                      targetLang = temp
                      // If translation is shown, put target word in input, else clear
                      text =
                          if (pageState is TranslateViewState.Translated) {
                            val translated = pageState as TranslateViewState.Translated
                            when (val translationState = translated.translation) {
                              is TranslationState.Translated ->
                                  translationState.translation.translatedWord
                              else -> ""
                            }
                          } else {
                            ""
                          }
                      // Clear results
                      viewModel.searchArticles("", "")
                    }) {
                      Icon(
                          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                          contentDescription = "Swap languages")
                    }
                LanguageSelector(
                    selectedLanguage = targetLang, onLanguageSelected = { targetLang = it })
              }
          Spacer(modifier = Modifier.height(16.dp))

          // Text input with search action
          OutlinedTextField(
              value = text,
              onValueChange = { text = it },
              label = { Text("Word to translate") },
              leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
              },
              trailingIcon = {
                if (text.isNotEmpty()) {
                  IconButton(onClick = { text = "" }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                  }
                }
              },
              keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
              keyboardActions =
                  KeyboardActions(onSearch = { viewModel.searchArticles(sourceLang, text) }),
              singleLine = true,
              modifier = Modifier.fillMaxWidth())

          Spacer(modifier = Modifier.height(16.dp))

          when (pageState) {
            is TranslateViewState.Empty -> {
              Column(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enter a word to translate",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                  }
            }
            is TranslateViewState.FetchingOptions -> CircularProgressIndicator()
            is TranslateViewState.OptionsFetched -> {
              val pageState = (pageState as TranslateViewState.OptionsFetched)
              AvailableSourceArticles(
                  pageState, viewModel, sourceLang, targetLang, snackbarHostState)
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
}

@Composable
fun ShowTranslatedArticle(translated: TranslateViewState.Translated) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
        text = translated.term.title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant)
    Text(
        text = translated.sourceLang.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(16.dp))

    when (translated.translation) {
      is TranslationState.Translated -> {
        val translationData = translated.translation.translation
        Text(
            text = translationData.translatedWord,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary)
        Text(
            text = translationData.targetLangCode.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text =
                translationData.targetShortDescription
                    ?: translationData.targetSummary
                    ?: "No description",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface)

        // Action buttons
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
          IconButton(onClick = { /* TODO: Implement bookmark */ }) {
            Icon(
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark",
                tint = MaterialTheme.colorScheme.primary)
          }
          IconButton(onClick = { /* TODO: Implement copy */ }) {
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy translation",
                tint = MaterialTheme.colorScheme.primary)
          }
          IconButton(onClick = { /* TODO: Implement open */ }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                contentDescription = "Open article",
                tint = MaterialTheme.colorScheme.primary)
          }
        }
      }

      is TranslationState.MissingTranslation -> {
        val availableTranslations =
            translated.translation.availableTranslations.joinToString(", ") {
              it.targetLangCode.uppercase()
            }
        Text(
            text = "Translation not available for ${translated.targetLang.uppercase()}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Available translations: $availableTranslations",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
      }

      else -> {
        Text(
            text =
                "${translated.term.title} (${translated.sourceLang}) -> state type ${translated.translation.javaClass}",
            style = MaterialTheme.typography.bodyMedium)
      }
    }
  }
}

@Composable
fun AvailableSourceArticles(
    pageState: TranslateViewState.OptionsFetched,
    viewModel: TranslateViewModel,
    sourceLang: String,
    targetLang: String,
    snackbarHostState: SnackbarHostState
) {
  val translatedArticles = pageState.options.filter { targetLang in it.availableLanguages }
  val untranslatedArticles = pageState.options.filter { targetLang !in it.availableLanguages }
  val coroutineScope = rememberCoroutineScope()

  LazyColumn {
    items(translatedArticles) { item ->
      val translationState = pageState.translations[item]
      val errorMessage =
          if (translationState is TranslationState.Error) translationState.errorMessage else null
      SearchResultItem(
          result = item,
          targetLang = targetLang,
          showAvailableLanguages = false,
          isLoading = translationState is TranslationState.Translating,
          errorMessage = errorMessage,
          onClick = {
            if (errorMessage != null) {
              coroutineScope.launch { snackbarHostState.showSnackbar(errorMessage) }
            } else {
              viewModel.fetchTranslation(pageState, item, sourceLang, targetLang)
            }
          })
    }

    if (translatedArticles.isNotEmpty() && untranslatedArticles.isNotEmpty()) {
      item {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
              HorizontalDivider(
                  modifier = Modifier.fillMaxWidth(0.3f),
                  color = MaterialTheme.colorScheme.outlineVariant)
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                  text = "Untranslated Articles".uppercase(),
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant)
              Spacer(modifier = Modifier.height(8.dp))
              HorizontalDivider(
                  modifier = Modifier.fillMaxWidth(0.3f),
                  color = MaterialTheme.colorScheme.outlineVariant)
            }
      }
    }

    items(untranslatedArticles) { item ->
      val translationState = pageState.translations[item]
      val errorMessage =
          if (translationState is TranslationState.Error) translationState.errorMessage else null
      SearchResultItem(
          result = item,
          targetLang = targetLang,
          showAvailableLanguages = true,
          isLoading = translationState is TranslationState.Translating,
          errorMessage = errorMessage,
          onClick = {
            if (errorMessage != null) {
              coroutineScope.launch { snackbarHostState.showSnackbar(errorMessage) }
            } else {
              viewModel.fetchTranslation(pageState, item, sourceLang, targetLang)
            }
          })
    }
  }
}

@Composable
fun SearchResultItem(
    result: OptionalSourceTerm,
    targetLang: String,
    showAvailableLanguages: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onClick: () -> Unit
) {
  Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable(onClick = onClick)) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = result.title, style = MaterialTheme.typography.titleLarge)
      Spacer(modifier = Modifier.height(4.dp))
      if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
      } else if (errorMessage != null) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Unknown error occurred",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error)
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
              text = errorMessage,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      } else if (showAvailableLanguages) {
        Text(
            text =
                "Available in: ${result.availableLanguages.joinToString(", ") { it.uppercase() }}",
            style = MaterialTheme.typography.bodyMedium,
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
