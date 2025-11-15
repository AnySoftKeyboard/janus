package com.anysoftkeyboard.janus.app.ui.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.ui.components.BookmarkButton
import com.anysoftkeyboard.janus.app.ui.components.CopyToClipboardButton
import com.anysoftkeyboard.janus.app.ui.components.WikipediaLinkButton
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewState
import com.anysoftkeyboard.janus.app.viewmodels.TranslationState

/**
 * Displays a translated article with source and target information.
 *
 * Shows:
 * - Source article title and language with Wikipedia link
 * - Translation result (translated word, missing translation, or other state)
 * - Action buttons (bookmark, copy)
 *
 * @param translated The translation state containing source and translation data
 */
@Composable
fun TranslationView(translated: TranslateViewState.Translated) {
  Column(modifier = Modifier.fillMaxWidth()) {
    SourceArticleHeader(
        title = translated.term.title,
        language = translated.sourceLang,
        pageId = translated.term.pageid)

    Spacer(modifier = Modifier.height(16.dp))

    TranslationContent(translation = translated.translation, targetLang = translated.targetLang)
  }
}

/** Header showing source article title, language, and Wikipedia link. */
@Composable
private fun SourceArticleHeader(title: String, language: String, pageId: Long) {
  Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
              text = title,
              style = MaterialTheme.typography.titleLarge,
              color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(
              text = language.uppercase(),
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        WikipediaLinkButton(
            url = "https://${language}.wikipedia.org/?curid=${pageId}",
            contentDescription = "Open source article",
            tint = MaterialTheme.colorScheme.onSurfaceVariant)
      }
}

/** Content area displaying the translation result based on state. */
@Composable
private fun TranslationContent(translation: TranslationState, targetLang: String) {
  when (translation) {
    is TranslationState.Translated -> {
      TranslatedContent(translation)
    }
    is TranslationState.MissingTranslation -> {
      MissingTranslationContent(targetLang, translation)
    }
    else -> {
      UnknownStateContent(translation)
    }
  }
}

/** Displays successfully translated content with target article and actions. */
@Composable
private fun TranslatedContent(translation: TranslationState.Translated) {
  val translationData = translation.translation

  // Target article header with title and Wikipedia link
  Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
              text = translationData.translatedWord,
              style = MaterialTheme.typography.displaySmall,
              color = MaterialTheme.colorScheme.primary)
          Text(
              text = translationData.targetLangCode.uppercase(),
              style = MaterialTheme.typography.labelLarge,
              color = MaterialTheme.colorScheme.primary)
        }
        WikipediaLinkButton(
            url = translationData.targetArticleUrl, contentDescription = "Open target article")
      }

  Spacer(modifier = Modifier.height(8.dp))

  // Description
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
    BookmarkButton()
    CopyToClipboardButton(text = translationData.translatedWord)
  }
}

/** Displays message when translation is not available in target language. */
@Composable
private fun MissingTranslationContent(
    targetLang: String,
    missingTranslation: TranslationState.MissingTranslation
) {
  val availableTranslations =
      missingTranslation.availableTranslations.joinToString(", ") { it.targetLangCode.uppercase() }

  Text(
      text = "Translation not available for ${targetLang.uppercase()}",
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.error)
  Spacer(modifier = Modifier.height(8.dp))
  Text(
      text = "Available translations: $availableTranslations",
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant)
}

/** Fallback content for unknown translation states. */
@Composable
private fun UnknownStateContent(translation: TranslationState) {
  Text(
      text = "Unknown state type: ${translation.javaClass}",
      style = MaterialTheme.typography.bodyMedium)
}
