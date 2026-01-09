package com.anysoftkeyboard.janus.app.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * Icon button that opens a Wikipedia article URL in the browser.
 *
 * @param url The Wikipedia article URL to open
 * @param contentDescription Accessibility description for the button
 * @param tint Color tint for the icon
 */
@Composable
fun WikipediaLinkButton(
    url: String,
    contentDescription: String = "Open in Wikipedia",
    tint: Color = MaterialTheme.colorScheme.primary,
) {
  val context = LocalContext.current
  IconButton(
      onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
      }
  ) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
        contentDescription = contentDescription,
        tint = tint,
    )
  }
}

/**
 * Icon button that copies text to the clipboard.
 *
 * @param text The text to copy to clipboard
 * @param contentDescription Accessibility description for the button
 * @param tint Color tint for the icon
 * @param onClick Callback when copy is completed
 */
@Composable
fun CopyToClipboardButton(
    text: String,
    contentDescription: String = "Copy to clipboard",
    tint: Color = MaterialTheme.colorScheme.primary,
    onClick: (() -> Unit)? = null,
) {
  val context = LocalContext.current
  IconButton(
      onClick = {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("article_title", text)
        clipboard.setPrimaryClip(clip)
        onClick?.invoke()
      }
  ) {
    Icon(
        imageVector = Icons.Default.ContentCopy,
        contentDescription = contentDescription,
        tint = tint,
    )
  }
}

/**
 * Icon button that bookmarks/unbookmarks an item.
 *
 * @param isBookmarked Whether the item is currently bookmarked
 * @param contentDescription Accessibility description for the button
 * @param tint Color tint for the icon
 * @param onClick Callback when bookmark button is clicked
 */
@Composable
fun BookmarkButton(
    isBookmarked: Boolean = false,
    contentDescription: String = "Bookmark",
    tint: Color = MaterialTheme.colorScheme.primary,
    onClick: (() -> Unit)? = null,
) {
  IconButton(
      onClick = {
        // TODO: Implement bookmark
        onClick?.invoke()
      }
  ) {
    Icon(
        imageVector = Icons.Default.BookmarkBorder,
        contentDescription = contentDescription,
        tint = tint,
    )
  }
}
