package com.anysoftkeyboard.janus.app.ui.items

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.components.CopyToClipboardButton
import com.anysoftkeyboard.janus.app.ui.components.HtmlText
import com.anysoftkeyboard.janus.app.ui.components.PivotConnector
import com.anysoftkeyboard.janus.app.ui.components.WikipediaLinkButton
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HistoryItem(
    translation: UiTranslation,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    unfocused: Boolean = false,
    sharedTransitionScope: SharedTransitionScope,
    onClick: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
  val interactionSource = remember { MutableInteractionSource() }
  val blurRadius by animateDpAsState(targetValue = if (unfocused) 2.dp else 0.dp, label = "blur")
  val padding by animateDpAsState(targetValue = if (unfocused) 8.dp else 0.dp, label = "padding")
  val saturation by
      animateFloatAsState(targetValue = if (unfocused) 0.05f else 1f, label = "saturation")
  val scale by animateFloatAsState(targetValue = if (unfocused) 0.8f else 1f, label = "scale")

  Card(
      modifier =
          modifier
              .fillMaxWidth()
              .animateContentSize()
              .blur(radius = blurRadius, edgeTreatment = BlurredEdgeTreatment.Unbounded)
              .padding(horizontal = padding)
              .drawWithContent {
                if (saturation < 0.99f || scale < 0.99f) {
                  val matrix = ColorMatrix()
                  matrix.setToSaturation(saturation)
                  val scaleMatrix = ColorMatrix().apply { setToScale(scale, scale, scale, 1f) }
                  matrix.timesAssign(scaleMatrix)

                  val filter = ColorFilter.colorMatrix(matrix)
                  val paint = Paint().apply { colorFilter = filter }
                  drawIntoCanvas { canvas ->
                    canvas.saveLayer(Rect(Offset.Zero, size), paint)
                    drawContent()
                    canvas.restore()
                  }
                } else {
                  drawContent()
                }
              }) {
        sharedTransitionScope.apply {
          AnimatedContent(
              targetState = isExpanded,
              label = "expand_collapse",
              modifier =
                  Modifier.clickable(
                      interactionSource = interactionSource,
                      indication = null,
                      onClick = onClick)) { targetExpanded ->
                if (targetExpanded) {
                  ExpandedHistoryItem(
                      translation, sharedTransitionScope, this@AnimatedContent, onDelete)
                } else {
                  CondensedHistoryItem(translation, sharedTransitionScope, this@AnimatedContent)
                }
              }
        }
      }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CondensedHistoryItem(
    translation: UiTranslation,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
  with(sharedTransitionScope) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Top Row: Language Pair
      Text(
          text =
              "${translation.sourceLang.uppercase()} \u2192 ${translation.targetLang.uppercase()}",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.secondary)

      Spacer(modifier = Modifier.height(8.dp))

      // Middle Row: The Pivot
      Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = translation.sourceWord,
            style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier =
                Modifier.weight(1f)
                    .sharedElementModifier(
                        sharedTransitionScope = sharedTransitionScope,
                        key = "source_title_${translation.timestamp}",
                        animatedVisibilityScope = animatedVisibilityScope),
            textAlign = androidx.compose.ui.text.style.TextAlign.Start)

        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            modifier =
                Modifier.padding(horizontal = 8.dp)
                    .size(24.dp)
                    .sharedElementModifier(
                        sharedTransitionScope = sharedTransitionScope,
                        key = "icon_${translation.timestamp}",
                        animatedVisibilityScope = animatedVisibilityScope))

        Text(
            text = translation.targetWord,
            style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Serif),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier =
                Modifier.weight(1f)
                    .sharedElementModifier(
                        sharedTransitionScope = sharedTransitionScope,
                        key = "target_title_${translation.timestamp}",
                        animatedVisibilityScope = animatedVisibilityScope),
            textAlign = androidx.compose.ui.text.style.TextAlign.End)
      }

      // Bottom Row: Context
      translation.sourceShortDescription?.let { description ->
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)
      }
    }
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ExpandedHistoryItem(
    translation: UiTranslation,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onDelete: () -> Unit
) {
  with(sharedTransitionScope) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Source Section
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                  text = translation.sourceWord,
                  style =
                      MaterialTheme.typography.headlineSmall.copy(fontFamily = FontFamily.Serif),
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis,
                  modifier =
                      Modifier.horizontalScroll(rememberScrollState())
                          .sharedElementModifier(
                              sharedTransitionScope = sharedTransitionScope,
                              key = "source_title_${translation.timestamp}",
                              animatedVisibilityScope = animatedVisibilityScope))
              Text(
                  text = translation.sourceLang.uppercase(),
                  style = MaterialTheme.typography.labelMedium,
                  color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row {
              CopyToClipboardButton(
                  text = translation.sourceWord,
                  contentDescription = "Copy source title",
                  tint = MaterialTheme.colorScheme.onSurfaceVariant)
              WikipediaLinkButton(
                  url = translation.sourceArticleUrl,
                  contentDescription = "Open source article",
                  tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }

      translation.sourceShortDescription?.let { description ->
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface)
      }

      translation.sourceSummary?.let { snippet ->
        Spacer(modifier = Modifier.height(4.dp))
        HtmlText(html = snippet, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Pivot Connector
      PivotConnector(
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = animatedVisibilityScope,
          sharedElementKey = "icon_${translation.timestamp}")

      Spacer(modifier = Modifier.height(16.dp))

      // Target Section
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                  text = translation.targetWord,
                  style =
                      MaterialTheme.typography.headlineSmall.copy(fontFamily = FontFamily.Serif),
                  color = MaterialTheme.colorScheme.primary,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis,
                  modifier =
                      Modifier.horizontalScroll(rememberScrollState())
                          .sharedElementModifier(
                              sharedTransitionScope = sharedTransitionScope,
                              key = "target_title_${translation.timestamp}",
                              animatedVisibilityScope = animatedVisibilityScope))
              Text(
                  text = translation.targetLang.uppercase(),
                  style = MaterialTheme.typography.labelLarge,
                  color = MaterialTheme.colorScheme.primary)
            }
            Row {
              CopyToClipboardButton(
                  text = translation.targetWord,
                  contentDescription = "Copy translated title",
                  tint = MaterialTheme.colorScheme.primary)
              WikipediaLinkButton(
                  url = translation.targetArticleUrl,
                  contentDescription = "Open target article",
                  tint = MaterialTheme.colorScheme.primary)
            }
          }

      translation.targetShortDescription?.let { description ->
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface)
      }

      translation.targetSummary?.let { summary ->
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = summary,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
      }

      Spacer(modifier = Modifier.height(16.dp))

      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        TextButton(onClick = onDelete) {
          Icon(
              imageVector = Icons.Outlined.Delete,
              contentDescription = "Remove translation",
              tint = MaterialTheme.colorScheme.error)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
              text = "REMOVE",
              style = MaterialTheme.typography.labelLarge,
              color = MaterialTheme.colorScheme.error)
        }
      }
    }
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Modifier.sharedElementModifier(
    sharedTransitionScope: SharedTransitionScope,
    key: String,
    animatedVisibilityScope: AnimatedVisibilityScope
): Modifier {
  with(sharedTransitionScope) {
    return this@sharedElementModifier.sharedElement(
        sharedContentState = rememberSharedContentState(key = key),
        animatedVisibilityScope = animatedVisibilityScope)
  }
}
