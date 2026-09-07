package com.anysoftkeyboard.janus.app.ui.states

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.repository.RelatedArticle
import com.anysoftkeyboard.janus.app.ui.components.CopyToClipboardButton
import com.anysoftkeyboard.janus.app.ui.components.HtmlText
import com.anysoftkeyboard.janus.app.ui.components.JanusLoader
import com.anysoftkeyboard.janus.app.ui.components.PivotConnector
import com.anysoftkeyboard.janus.app.ui.components.WikipediaLinkButton
import com.anysoftkeyboard.janus.app.viewmodels.RelatedArticlesState
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
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    relatedState: RelatedArticlesState = RelatedArticlesState.Hidden,
    onRelatedClick: ((RelatedArticle) -> Unit)? = null,
) {
  Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
    SourceArticleSection(
        title = translated.term.title,
        language = translated.sourceLang,
        pageId = translated.term.pageid,
        snippet = translated.term.snippet,
        translation = translated.translation,
    )

    PivotConnector(
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
    )

    TranslationContent(translation = translated.translation, targetLang = translated.targetLang)

    RelatedArticlesSection(
        relatedState = relatedState,
        sourceLang = translated.sourceLang,
        onRelatedClick = onRelatedClick,
    )
  }
}

/** Section showing source article with title, language, snippet, and description. */
@Composable
private fun SourceArticleSection(
    title: String,
    language: String,
    pageId: Long,
    snippet: String,
    translation: TranslationState,
) {
  Card(
      colors =
          CardDefaults.cardColors(
              containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
          ),
      shape = MaterialTheme.shapes.medium,
  ) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
      // Header with title and action buttons
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
              text = title,
              style = MaterialTheme.typography.headlineSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier.horizontalScroll(rememberScrollState()),
          )
          Text(
              text = language.uppercase(),
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        Row {
          CopyToClipboardButton(
              text = title,
              contentDescription = "Copy source title",
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
          )
          WikipediaLinkButton(
              url = "https://${language}.wikipedia.org/?curid=${pageId}",
              contentDescription = "Open source article",
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
          )
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
              color = MaterialTheme.colorScheme.onSurface,
          )
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
      shape = MaterialTheme.shapes.medium,
  ) {
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
        verticalAlignment = Alignment.CenterVertically,
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
            text = translationData.translatedWord,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.horizontalScroll(rememberScrollState()),
        )
        Text(
            text = translationData.targetLangCode.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
      }
      Row {
        CopyToClipboardButton(
            text = translationData.translatedWord,
            contentDescription = "Copy translated title",
            tint = MaterialTheme.colorScheme.primary,
        )
        WikipediaLinkButton(
            url = translationData.targetArticleUrl,
            contentDescription = "Open target article",
            tint = MaterialTheme.colorScheme.primary,
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Target short description
    translationData.targetShortDescription?.let { description ->
      Text(
          text = description,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface,
      )
      Spacer(modifier = Modifier.height(4.dp))
    }

    // Target summary
    translationData.targetSummary?.let { summary ->
      Text(
          text = summary,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}

/** Displays message when translation is not available in target language. */
@Composable
private fun MissingTranslationContent(
    targetLang: String,
    missingTranslation: TranslationState.MissingTranslation,
) {
  val availableTranslations =
      missingTranslation.availableTranslations.joinToString(", ") { it.targetLangCode.uppercase() }

  Text(
      text = stringResource(R.string.translation_not_available, targetLang.uppercase()),
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.error,
  )
  Spacer(modifier = Modifier.height(8.dp))
  Text(
      text = stringResource(R.string.available_translations, availableTranslations),
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
  )
}

/** Fallback content for unknown translation states. */
@Composable
private fun UnknownStateContent(translation: TranslationState) {
  Text(
      text = "Unknown state type: ${translation.javaClass}",
      style = MaterialTheme.typography.bodyMedium,
  )
}

/** Inline section showing articles related to the translated source article. */
@Composable
private fun RelatedArticlesSection(
    relatedState: RelatedArticlesState,
    sourceLang: String,
    onRelatedClick: ((RelatedArticle) -> Unit)?,
) {
  when (relatedState) {
    is RelatedArticlesState.Hidden -> {
      // Do not disturb the translation view when related articles are unavailable.
    }
    is RelatedArticlesState.Loading -> {
      Spacer(modifier = Modifier.height(8.dp))
      Row(
          modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        JanusLoader(modifier = Modifier.size(48.dp))
        Text(
            text = stringResource(R.string.related_articles_loading),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }
    is RelatedArticlesState.Loaded -> {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
          text = stringResource(R.string.related_articles_title),
          style = MaterialTheme.typography.labelLarge,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Spacer(modifier = Modifier.height(8.dp))
      relatedState.articles.forEach { article ->
        RelatedArticleItem(
            article = article,
            sourceLang = sourceLang,
            onClick = onRelatedClick?.let { callback -> { callback(article) } },
        )
      }
    }
  }
}

/** Card displaying a single related article. Tapping it translates that concept directly. */
@Composable
private fun RelatedArticleItem(
    article: RelatedArticle,
    sourceLang: String,
    onClick: (() -> Unit)?,
) {
  Card(
      modifier =
          Modifier.fillMaxWidth().padding(vertical = 4.dp).testTag("related_article_item").let {
              modifier ->
            if (onClick != null) modifier.clickable(onClick = onClick) else modifier
          },
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      shape = MaterialTheme.shapes.medium,
  ) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
      Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f).horizontalScroll(rememberScrollState()),
        )
        WikipediaLinkButton(
            url = "https://${sourceLang}.wikipedia.org/?curid=${article.pageid}",
            contentDescription = stringResource(R.string.content_description_open_wikipedia),
        )
      }
      article.shortDescription?.let { description ->
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
      }
      article.extract?.let { extract ->
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = extract,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
      }
    }
  }
}
