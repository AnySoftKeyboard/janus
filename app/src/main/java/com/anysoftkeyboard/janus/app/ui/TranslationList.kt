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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TranslationList(groupedTranslations: Map<String, List<UiTranslation>>) {
  LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    groupedTranslations.forEach { (header, translations) ->
      stickyHeader { TranslationHeader(header) }
      items(translations) { translation -> TranslationCard(translation) }
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
