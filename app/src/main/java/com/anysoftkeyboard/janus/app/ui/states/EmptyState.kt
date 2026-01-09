package com.anysoftkeyboard.janus.app.ui.states

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.components.JanusLoader
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewModel.ErrorType
import com.anysoftkeyboard.janus.app.viewmodels.TranslateViewState

/**
 * Generic empty state component with icon, title, and message.
 *
 * @param iconContent Icon painter to display
 * @param title Main title text
 * @param message Detailed message text
 * @param iconTint Color tint for the icon
 */
@Composable
private fun EmptyStateMessage(
    title: String,
    message: String,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    iconContent: @Composable () -> Unit,
) {
  Column(
      modifier = modifier.fillMaxWidth().padding(28.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
  ) {
    iconContent()
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = title, style = MaterialTheme.typography.titleMedium, color = iconTint)
    if (message.isNotEmpty()) {
      Spacer(modifier = Modifier.height(4.dp))
      Text(
          text = message,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.secondary,
      )
    }
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun EmptyStateMessageWithPainter(
    title: String,
    message: String,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    painter: Painter,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
  EmptyStateMessage(title, message, iconTint, modifier) {
    val modifier =
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
          with(sharedTransitionScope) {
            Modifier.sharedElement(
                sharedContentState = rememberSharedContentState(key = "shared_icon"),
                animatedVisibilityScope = animatedVisibilityScope,
            )
          }
        } else {
          Modifier
        }
    Icon(
        painter = painter,
        contentDescription = title,
        modifier = Modifier.size(128.dp).then(modifier),
        tint = iconTint,
    )
  }
}

/** Initial empty state shown when the app starts. */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun InitialEmptyState(
    welcomeMessage: String,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
  EmptyStateMessageWithPainter(
      title = welcomeMessage,
      message = "",
      iconTint = MaterialTheme.colorScheme.primary,
      painter = painterResource(R.mipmap.ic_launcher_foreground),
      sharedTransitionScope = sharedTransitionScope,
      animatedVisibilityScope = animatedVisibilityScope,
  )
}

/** Loading state with a progress indicator. */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun LoadingState(
    message: String,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
  EmptyStateMessage(title = message, message = "", iconTint = MaterialTheme.colorScheme.primary) {
    val modifier =
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
          with(sharedTransitionScope) {
            Modifier.sharedElement(
                sharedContentState = rememberSharedContentState(key = "shared_icon"),
                animatedVisibilityScope = animatedVisibilityScope,
            )
          }
        } else {
          Modifier
        }
    JanusLoader(modifier = Modifier.size(128.dp).then(modifier))
  }
}

/**
 * No results found state, displayed when search returns empty results.
 *
 * @param searchTerm The term that was searched for
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NoResultsState(searchTerm: String) {
  EmptyStateMessageWithPainter(
      title = stringResource(R.string.empty_state_no_results_title),
      message = stringResource(R.string.empty_state_no_results_message, searchTerm),
      painter = rememberVectorPainter(image = Icons.Default.Search),
      modifier = Modifier.testTag("no_results_state"),
  )
} // TODO: Add Modifier parameter to EmptyStateMessageWithPainter to properly support testTag? Or

// just wrap it?
// SearchResultsView calls NoResultsState.
// Let's look at EmptyStateMessageWithPainter. It wraps EmptyStateMessage which takes modifier in
// the lambda for Icon but not for the column.
// I will wrap the content in a Box or Column with the tag if I can't easily pipe it.

// Actually EmptyStateMessage takes a lambda for icon.
// EmptyStateMessageWithPainter calls EmptyStateMessage.
// EmptyStateMessage creates a Column.

// I'll update EmptyStateMessageWithPainter to take a modifier and pass it to EmptyStateMessage? No,
// EmptyStateMessage doesn't take a modifier for the root Column.
// I should update EmptyStateMessage to accept a modifier for the root Column.

/**
 * Error state display.
 *
 * Shows error information visually.
 *
 * @param error The error state containing error type and message
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ErrorStateDisplay(error: TranslateViewState.Error) {
  val (titleRes, messageRes, icon) =
      when (error.errorType) {
        ErrorType.Network ->
            Triple(
                R.string.error_network_title,
                R.string.error_network_message,
                Icons.Default.Warning,
            )
        ErrorType.RateLimit ->
            Triple(
                R.string.error_rate_limit_title,
                R.string.error_rate_limit_message,
                Icons.Default.Warning,
            )
        ErrorType.NotFound ->
            Triple(
                R.string.error_not_found_title,
                R.string.error_not_found_message,
                Icons.Default.Warning,
            )
        ErrorType.Server ->
            Triple(
                R.string.error_server_title,
                R.string.error_server_message,
                Icons.Default.Warning,
            )
        ErrorType.Unknown ->
            Triple(
                R.string.error_unknown_title,
                0, // Use error message from exception if available, or generic
                Icons.Default.Warning,
            )
      }

  val message =
      if (messageRes != 0) stringResource(messageRes)
      else error.errorMessage ?: stringResource(R.string.error_unknown)

  EmptyStateMessageWithPainter(
      title = stringResource(titleRes),
      message = message,
      painter = rememberVectorPainter(image = icon),
      modifier = Modifier.testTag("error_state"),
  )
}
