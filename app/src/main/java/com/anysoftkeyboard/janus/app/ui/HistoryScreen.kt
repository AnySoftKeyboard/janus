package com.anysoftkeyboard.janus.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.components.SearchInputField
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import com.anysoftkeyboard.janus.app.ui.util.HistoryGrouper
import com.anysoftkeyboard.janus.app.viewmodels.HistoryViewModel
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
  val history by viewModel.history.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  Scaffold(
      snackbarHost = {
        SnackbarHost(snackbarHostState) { data ->
          Snackbar(
              modifier = Modifier.padding(12.dp),
              action = {
                data.visuals.actionLabel?.let { label ->
                  TextButton(onClick = { data.performAction() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Undo, contentDescription = label)
                  }
                }
              },
          ) {
            Text(data.visuals.message)
          }
        }
      }
  ) { paddingValues ->
    Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
      // Search field
      SearchInputField(
          text = searchQuery,
          onTextChange = { viewModel.updateSearchQuery(it) },
          onSearch = { /* No-op for history search, filtering is real-time */ },
          label = stringResource(R.string.search_history_label),
      )

      // Results or empty state
      if (history.isEmpty() && searchQuery.isNotBlank()) {
        EmptySearchResults(query = searchQuery)
      } else {
        val context = LocalContext.current
        val translationRemovedMessage = stringResource(R.string.translation_removed)
        val undoLabel = stringResource(R.string.action_undo)
        val uiTranslations = remember(history) { history.map { UiTranslation.fromTranslation(it) } }
        val groupedTranslations =
            remember(uiTranslations, context) { HistoryGrouper.group(context, uiTranslations) }

        HistoryItemsList(
            groupedTranslations = groupedTranslations,
            onDelete = { translation ->
              viewModel.deleteTranslation(translation.id)
              scope.launch {
                val result =
                    snackbarHostState.showSnackbar(
                        message = translationRemovedMessage,
                        actionLabel = undoLabel,
                        duration = SnackbarDuration.Short,
                        withDismissAction = true,
                    )
                if (result == SnackbarResult.ActionPerformed) {
                  viewModel.restoreTranslation(translation)
                }
              }
            },
        )
      }
    }
  }
}

@Composable
private fun EmptySearchResults(query: String) {
  Column(
      modifier = Modifier.fillMaxSize().padding(32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
  ) {
    Icon(
        imageVector = Icons.Default.SearchOff,
        contentDescription = null,
        modifier = Modifier.size(64.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(R.string.no_history_results, query),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
    )
  }
}
