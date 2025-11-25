package com.anysoftkeyboard.janus.app.ui.states

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.components.CopyToClipboardButton
import com.anysoftkeyboard.janus.app.ui.components.HtmlText
import com.anysoftkeyboard.janus.app.ui.components.PivotConnector
import com.anysoftkeyboard.janus.app.ui.components.WikipediaLinkButton
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewState
import com.anysoftkeyboard.janus.app.viewmodels.TranslationState

/**
 * Displays a translated article with source and target information.
 *
 * Shows:
 * - Source article title, language, snippet, and description with Wikipedia link
 * - Translation result (translated word, missing translation, or other state)
 * - Target article description and summary
 * - Action buttons (bookmark, copy)
 *
 * @param translated The translation state containing source and translation data
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TranslationView(
    translated: TranslateViewState.Translated,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    SourceArticleSection(
        title = translated.term.title,
        language = translated.sourceLang,
        pageId = translated.term.pageid,
        snippet = translated.term.snippet,
        translation = translated.translation)

    PivotConnector(
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope)

    TranslationContent(translation = translated.translation, targetLang = translated.targetLang)
  }
}

/** Section showing source article with title, language, snippet, and description. */
@Composable
private fun SourceArticleSection(
    title: String,
    language: String,
    pageId: Long,
    snippet: String,
    translation: TranslationState
) {
  Card(
      colors =
          CardDefaults.cardColors(
              containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
      shape = MaterialTheme.shapes.medium) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
          // Header with title and action buttons
          Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                      text = title,
                      style = MaterialTheme.typography.headlineSmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      maxLines = 1,
                      overflow = TextOverflow.Ellipsis,
                      modifier = Modifier.horizontalScroll(rememberScrollState()))
                  Text(
                      text = language.uppercase(),
                      style = MaterialTheme.typography.labelMedium,
                      color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Row {
                  CopyToClipboardButton(
                      text = title,
                      contentDescription = "Copy source title",
                      tint = MaterialTheme.colorScheme.onSurfaceVariant)
                  WikipediaLinkButton(
                      url = "https://${language}.wikipedia.org/?curid=${pageId}",
                      contentDescription = "Open source article",
                      tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
              }

          // Show short description and snippet if available
          if (translation is TranslationState.Translated) {
            val translationData = translation.translation
            Spacer(modifier = Modifier.height(8.dp))

            // Short description
            translationData.sourceShortDescription?.let { description ->
              Text(
                  text = description,
                  style = MaterialTheme.typography.bodyMedium,
                  color = MaterialTheme.colorScheme.onSurface)
              Spacer(modifier = Modifier.height(4.dp))
            }

            // Snippet (HTML)
            if (snippet.isNotEmpty()) {
              HtmlText(html = snippet, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          } else if (snippet.isNotEmpty()) {
            // If no translation yet, still show snippet
            Spacer(modifier = Modifier.height(8.dp))
            HtmlText(html = snippet, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }
      }
}

/** Content area displaying the translation result based on state. */
@Composable
private fun TranslationContent(translation: TranslationState, targetLang: String) {
  Card(
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      shape = MaterialTheme.shapes.medium) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
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
      }
}

/** Displays successfully translated content with target article and actions. */
@Composable
private fun TranslatedContent(translation: TranslationState.Translated) {
  val translationData = translation.translation

  Column(modifier = Modifier.fillMaxWidth()) {
    // Target article header with title and action buttons
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
                text = translationData.translatedWord,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.horizontalScroll(rememberScrollState()))
            Text(
                text = translationData.targetLangCode.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary)
          }
          Row {
            CopyToClipboardButton(
                text = translationData.translatedWord,
                contentDescription = "Copy translated title",
                tint = MaterialTheme.colorScheme.primary)
            WikipediaLinkButton(
                url = translationData.targetArticleUrl,
                contentDescription = "Open target article",
                tint = MaterialTheme.colorScheme.primary)
          }
        }

    Spacer(modifier = Modifier.height(8.dp))

    // Target short description
    translationData.targetShortDescription?.let { description ->
      Text(
          text = description,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface)
      Spacer(modifier = Modifier.height(4.dp))
    }

    // Target summary
    translationData.targetSummary?.let { summary ->
      Text(
          text = summary,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
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
      text = stringResource(R.string.translation_not_available, targetLang.uppercase()),
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.error)
  Spacer(modifier = Modifier.height(8.dp))
  Text(
      text = stringResource(R.string.available_translations, availableTranslations),
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
