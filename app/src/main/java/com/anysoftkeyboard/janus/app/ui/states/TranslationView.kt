package com.anysoftkeyboard.janus.app.ui.states

import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.anysoftkeyboard.janus.app.ui.components.BookmarkButton
import com.anysoftkeyboard.janus.app.ui.components.CopyToClipboardButton
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
@Composable
fun TranslationView(translated: TranslateViewState.Translated) {
  Column(modifier = Modifier.fillMaxWidth()) {
    SourceArticleSection(
        title = translated.term.title,
        language = translated.sourceLang,
        pageId = translated.term.pageid,
        snippet = translated.term.snippet,
        translation = translated.translation)

    Spacer(modifier = Modifier.height(24.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(24.dp))

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
  Column(modifier = Modifier.fillMaxWidth()) {
    // Header with title and Wikipedia link
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

  Column(modifier = Modifier.fillMaxWidth()) {
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

    // Action buttons
    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
      BookmarkButton()
      CopyToClipboardButton(text = translationData.translatedWord)
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

/** Helper composable to display HTML text. */
@Composable
private fun HtmlText(html: String, color: androidx.compose.ui.graphics.Color) {
  AndroidView(
      factory = { context ->
        TextView(context).apply {
          movementMethod = LinkMovementMethod.getInstance()
          // Convert Compose Color to Android Color int
          val androidColor =
              android.graphics.Color.argb(
                  (color.alpha * 255).toInt(),
                  (color.red * 255).toInt(),
                  (color.green * 255).toInt(),
                  (color.blue * 255).toInt())
          setTextColor(androidColor)
        }
      },
      update = { textView -> setHtmlToText(textView, html) })
}

/** Helper function to set HTML content to TextView with proper API level handling. */
private fun setHtmlToText(view: TextView, html: String) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    view.text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
  } else {
    @Suppress("DEPRECATION") view.text = Html.fromHtml(html)
  }
}
