package com.anysoftkeyboard.janus.app.ui.components

import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView

/** Helper composable to display HTML text. */
@Composable
fun HtmlText(html: String, color: Color) {
  // Performance optimization: Parse HTML only when content changes.
  // HTML parsing is an expensive operation that shouldn't run on every recomposition.
  val spanned =
      remember(html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
        } else {
          @Suppress("DEPRECATION") Html.fromHtml(html)
        }
      }

  AndroidView(
      factory = { context ->
        TextView(context).apply { movementMethod = LinkMovementMethod.getInstance() }
      },
      update = { textView ->
        textView.text = spanned
        // Convert Compose Color to Android Color int and update.
        // Moving this to update block ensures color updates are applied.
        val androidColor =
            android.graphics.Color.argb(
                (color.alpha * 255).toInt(),
                (color.red * 255).toInt(),
                (color.green * 255).toInt(),
                (color.blue * 255).toInt(),
            )
        textView.setTextColor(androidColor)
      },
  )
}
