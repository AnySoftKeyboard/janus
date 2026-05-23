package com.anysoftkeyboard.janus.app.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.anysoftkeyboard.janus.app.BuildConfig
import com.anysoftkeyboard.janus.app.R
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

/**
 * About screen displaying application information, explanation of the unique translation mechanism
 * (The Colophon), links to GitHub repository and issue tracker, and the open-source licenses
 * attribution page.
 */
@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  Scaffold(modifier = modifier) { paddingValues ->
    Column(
        modifier =
            Modifier.fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      // 1. Header
      Icon(
          painter = painterResource(R.mipmap.ic_launcher_foreground),
          contentDescription = null,
          modifier = Modifier.size(96.dp),
          tint = MaterialTheme.colorScheme.primary,
      )
      Spacer(modifier = Modifier.height(12.dp))
      Text(
          text = stringResource(R.string.about_title),
          style = MaterialTheme.typography.headlineMedium,
          color = MaterialTheme.colorScheme.onSurface,
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
          text = stringResource(R.string.about_version, BuildConfig.VERSION_NAME),
          style = MaterialTheme.typography.labelLarge,
          color = MaterialTheme.colorScheme.secondary,
      )
      Spacer(modifier = Modifier.height(28.dp))

      // 2. Abstract
      Text(
          text = stringResource(R.string.about_abstract),
          style = MaterialTheme.typography.bodyLarge,
          color = MaterialTheme.colorScheme.onSurface,
          textAlign = TextAlign.Center,
      )
      Spacer(modifier = Modifier.height(28.dp))

      // 3. Methodology
      Text(
          text = stringResource(R.string.about_methodology_title),
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.primary,
          modifier = Modifier.align(Alignment.Start),
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
          text = stringResource(R.string.about_methodology_text),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.align(Alignment.Start),
      )
      Spacer(modifier = Modifier.height(28.dp))

      // 4. References
      ReferenceLinkRow(
          label = stringResource(R.string.about_link_source_code),
          url = "https://github.com/AnySoftKeyboard/janus",
          context = context,
      )
      Spacer(modifier = Modifier.height(4.dp))
      ReferenceLinkRow(
          label = stringResource(R.string.about_link_issue_tracker),
          url = "https://github.com/AnySoftKeyboard/janus/issues",
          context = context,
      )
      Spacer(modifier = Modifier.height(28.dp))

      // 5. Attributions
      OutlinedButton(
          onClick = { context.startActivity(Intent(context, OssLicensesMenuActivity::class.java)) },
          colors =
              ButtonDefaults.outlinedButtonColors(
                  contentColor = MaterialTheme.colorScheme.secondary
              ),
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
          modifier = Modifier.fillMaxWidth(),
      ) {
        Text(
            text = stringResource(R.string.about_button_licenses),
            style = MaterialTheme.typography.labelLarge,
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
private fun ReferenceLinkRow(
    label: String,
    url: String,
    context: Context,
    modifier: Modifier = Modifier,
) {
  Row(
      modifier =
          modifier
              .fillMaxWidth()
              .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
              }
              .padding(vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.weight(1f),
    )
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.size(20.dp),
    )
  }
}
