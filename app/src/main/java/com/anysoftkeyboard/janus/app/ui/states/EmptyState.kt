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
import androidx.compose.ui.graphics.vector.ImageVector
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
 * @param icon Icon to display
 * @param title Main title text
 * @param message Detailed message text
 * @param iconTint Color tint for the icon
 */
@Composable
fun EmptyStateMessage(
    icon: ImageVector,
    title: String,
    message: String,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
  EmptyStateMessage(
      painter = rememberVectorPainter(icon), title = title, message = message, iconTint = iconTint)
}

/**
 * Generic empty state component with icon, title, and message.
 *
 * @param painter Icon painter to display
 * @param title Main title text
 * @param message Detailed message text
 * @param iconTint Color tint for the icon
 */
@Composable
fun EmptyStateMessage(
    painter: Painter,
    title: String,
    message: String,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
  Column(
      modifier = Modifier.fillMaxWidth().padding(28.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center) {
        Icon(
            painter = painter,
            contentDescription = title,
            modifier = Modifier.size(128.dp),
            tint = iconTint)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium, color = iconTint)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
}

/** Initial empty state shown when the app starts. */
@Composable
fun InitialEmptyState(welcomeMessage: String) {
  EmptyStateMessage(
      painter = painterResource(R.mipmap.ic_launcher_foreground),
      title = welcomeMessage,
      message = "",
      iconTint = MaterialTheme.colorScheme.primary)
}

/** Loading state with a progress indicator. */
@Composable
fun LoadingState(message: String) {
  Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center) {
        JanusLoader(modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
}

/**
 * No results found state, displayed when search returns empty results.
 *
 * @param searchTerm The term that was searched for
 */
@Composable
fun NoResultsState(searchTerm: String) {
  EmptyStateMessage(
      icon = Icons.Default.Search,
      title = stringResource(R.string.empty_state_no_results_title),
      message = stringResource(R.string.empty_state_no_results_message, searchTerm))
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

  Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = stringResource(R.string.content_description_error),
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(128.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error.errorType,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = error.errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
}
