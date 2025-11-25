package com.anysoftkeyboard.janus.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
  val history by viewModel.history.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()

  Column(modifier = Modifier.fillMaxSize()) {
    // Search field
    SearchInputField(
            text = searchQuery,
            onTextChange = { viewModel.updateSearchQuery(it) },
            onSearch = { /* No-op for history search, filtering is real-time */},
            label = stringResource(R.string.search_history_label)
    )

    // Results or empty state
    if (history.isEmpty() && searchQuery.isNotBlank()) {
      EmptySearchResults(query = searchQuery)
    } else {
      val context = LocalContext.current
      val uiTranslations = history.map { UiTranslation.fromTranslation(it) }
      TranslationList(groupedTranslations = HistoryGrouper.group(context, uiTranslations))
    }
  }
}

@Composable
private fun EmptySearchResults(query: String) {
  Column(
          modifier = Modifier.fillMaxSize().padding(32.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
  ) {
    Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
            text = stringResource(R.string.no_history_results, query),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
    )
  }
}
