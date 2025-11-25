package com.anysoftkeyboard.janus.app.ui.items

import android.os.Build
import android.text.Html
import android.util.TypedValue
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.repository.OptionalSourceTerm
import com.anysoftkeyboard.janus.app.ui.components.JanusLoader
import com.anysoftkeyboard.janus.app.ui.components.WikipediaLinkButton

/**
 * Card displaying a single search result item.
 *
 * Shows the article title, snippet/status, and a Wikipedia link button. Handles loading, error, and
 * normal states.
 *
 * @param result The search result data
 * @param sourceLang Source language code for building Wikipedia URL
 * @param targetLang Target language code (not currently used in display)
 * @param showAvailableLanguages Whether to show list of available translations
 * @param isLoading Whether translation is currently loading
 * @param errorMessage Error message to display if translation failed
 * @param onClick Callback when the card is clicked, or null to disable clicking
 */
@Composable
fun SearchResultItem(
    result: OptionalSourceTerm,
    sourceLang: String,
    targetLang: String,
    showAvailableLanguages: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onClick: (() -> Unit)?
) {
  val modifier =
      Modifier.fillMaxWidth().padding(vertical = 4.dp).let {
        if (onClick != null) it.clickable(onClick = onClick) else it
      }

  Card(modifier = modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Title row with Wikipedia link button
      Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = result.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f))
        WikipediaLinkButton(
            url = "https://${sourceLang}.wikipedia.org/?curid=${result.pageid}",
            contentDescription = "Open in Wikipedia")
      }

      Spacer(modifier = Modifier.height(4.dp))

      // Content area: loading, error, available languages, or snippet
      when {
        errorMessage != null -> {
          ErrorContent(errorMessage)
        }
        showAvailableLanguages -> {
          AvailableLanguagesText(result.availableLanguages)
        }
        else -> {
          HtmlSnippet(
              snippet = result.snippet,
              textColor = MaterialTheme.colorScheme.onSurfaceVariant,
              textSize = MaterialTheme.typography.bodyMedium.fontSize)
        }
      }
    }
  }

  if (isLoading) {
    Spacer(modifier = Modifier.height(4.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
      JanusLoader(modifier = Modifier.size(48.dp))
      Text(
          text = stringResource(R.string.loading_translation_for_item, result.title),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }
}

/** Displays error state within the search result item. */
@Composable
private fun ErrorContent(errorMessage: String) {
  Column {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
          imageVector = Icons.Default.Warning,
          contentDescription = "Error",
          tint = MaterialTheme.colorScheme.error,
          modifier = Modifier.size(20.dp))
      Spacer(modifier = Modifier.size(4.dp))
      Text(
          text = stringResource(R.string.error_unknown),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.error)
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = errorMessage,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant)
  }
}

/** Displays list of available translation languages or a message if none available. */
@Composable
private fun AvailableLanguagesText(availableLanguages: List<String>) {
  val text =
      if (availableLanguages.isEmpty()) {
        stringResource(R.string.no_translations_available)
      } else {
        stringResource(
            R.string.available_in_languages,
            availableLanguages.joinToString(", ") { it.uppercase() })
      }

  Text(
      text = text,
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant)
}

/** Displays HTML snippet using AndroidView with TextView. */
@Composable
private fun HtmlSnippet(snippet: String, textColor: Color, textSize: TextUnit) {
  val textColorInt = textColor.toArgb()
  AndroidView(
      factory = { context -> TextView(context) },
      update = { textView ->
        textView.setTextColor(textColorInt)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.value)
        setHtmlToText(textView, snippet)
      })
}

/** Helper function to set HTML content to TextView with proper API level handling. */
private fun setHtmlToText(view: TextView, snippet: String) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    view.text = Html.fromHtml(snippet, Html.FROM_HTML_MODE_COMPACT)
  } else {
    @Suppress("DEPRECATION") view.text = Html.fromHtml(snippet)
  }
}
