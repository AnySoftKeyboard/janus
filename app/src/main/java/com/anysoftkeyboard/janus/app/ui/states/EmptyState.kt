package com.anysoftkeyboard.janus.app.ui.states

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.components.JanusLoader
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
    iconContent: @Composable () -> Unit
) {
  Column(
      modifier = Modifier.fillMaxWidth().padding(28.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center) {
        iconContent()
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium, color = iconTint)
        if (message.isNotEmpty()) {
          Spacer(modifier = Modifier.height(4.dp))
          Text(
              text = message,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.secondary)
        }
      }
}

@Composable
private fun EmptyStateMessageWithPainter(
    title: String,
    message: String,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    painter: Painter
) {
  EmptyStateMessage(title, message, iconTint) {
    Icon(
        painter = painter,
        contentDescription = title,
        modifier = Modifier.size(128.dp),
        tint = iconTint)
  }
}

/** Initial empty state shown when the app starts. */
@Composable
fun InitialEmptyState(welcomeMessage: String) {
  EmptyStateMessageWithPainter(
      title = welcomeMessage,
      message = "",
      iconTint = MaterialTheme.colorScheme.primary,
      painter = painterResource(R.mipmap.ic_launcher_foreground))
}

/** Loading state with a progress indicator. */
@Composable
fun LoadingState(message: String) {
  EmptyStateMessage(title = message, message = "", iconTint = MaterialTheme.colorScheme.primary) {
    JanusLoader(modifier = Modifier.size(128.dp))
  }
}

/**
 * No results found state, displayed when search returns empty results.
 *
 * @param searchTerm The term that was searched for
 */
@Composable
fun NoResultsState(searchTerm: String) {
  EmptyStateMessageWithPainter(
      title = stringResource(R.string.empty_state_no_results_title),
      message = stringResource(R.string.empty_state_no_results_message, searchTerm),
      painter = rememberVectorPainter(image = Icons.Default.Search))
}

/**
 * Error state display with snackbar notification.
 *
 * Shows error information both visually and via snackbar.
 *
 * @param error The error state containing error type and message
 * @param snackbarHostState State for showing snackbar notifications
 */
@Composable
fun ErrorStateDisplay(error: TranslateViewState.Error, snackbarHostState: SnackbarHostState) {
  // Show snackbar immediately when error occurs
  LaunchedEffect(error) {
    snackbarHostState.showSnackbar("${error.errorType}: ${error.errorMessage}")
  }
  EmptyStateMessageWithPainter(
      title = error.errorType,
      message = error.errorMessage,
      painter = rememberVectorPainter(image = Icons.Default.Warning))
}
