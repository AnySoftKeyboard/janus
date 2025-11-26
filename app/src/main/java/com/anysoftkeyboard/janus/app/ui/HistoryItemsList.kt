package com.anysoftkeyboard.janus.app.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import com.anysoftkeyboard.janus.app.ui.items.HistoryItem
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryItemsList(groupedTranslations: Map<String, List<UiTranslation>>) {
  var expandedItemId by remember { mutableStateOf<Long?>(null) }

  val listState = rememberLazyListState()

  // Monitor expanded item visibility
  LaunchedEffect(expandedItemId) {
    if (expandedItemId != null) {
      snapshotFlow { listState.layoutInfo }.collect { layoutInfo ->
        if (listState.isScrollInProgress) {
          val expandedItem = layoutInfo.visibleItemsInfo.find { it.key == expandedItemId }
          if (expandedItem != null) {
            val itemMidpoint = expandedItem.offset + (expandedItem.size / 2)
            if (itemMidpoint < 0 || itemMidpoint > layoutInfo.viewportEndOffset) {
              expandedItemId = null
            }
          } else {
            // Item is not visible at all
            expandedItemId = null
          }
        }
      }
    }
  }

  LazyColumn(
          state = listState,
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    groupedTranslations.forEach { (header, translations) ->
      stickyHeader { TranslationHeader(header) }
      items(items = translations, key = { it.timestamp }) { translation ->
        HistoryItem(
                translation = translation,
                isExpanded = expandedItemId == translation.timestamp,
                dimmed = expandedItemId != null && expandedItemId != translation.timestamp,
                onClick = {
                  expandedItemId =
                          if (expandedItemId == translation.timestamp) {
                            null
                          } else {
                            translation.timestamp
                          }
                }
        )
      }
    }
  }
}

@Composable
fun TranslationHeader(text: String) {
  Row(
          modifier =
                  Modifier.fillMaxWidth()
                          .background(MaterialTheme.colorScheme.background)
                          .padding(vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
            text = "●",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.width(8.dp))
    HorizontalDivider(modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurface)
  }
}
