package com.anysoftkeyboard.janus.app.ui.components

import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView

/** Helper composable to display HTML text. */
@Composable
fun HtmlText(html: String, color: Color) {
  AndroidView(
      factory = { context ->
        TextView(context).apply {
          movementMethod = LinkMovementMethod.getInstance()
          // Convert Compose Color to Android Color int
          val androidColor =
              android.graphics.Color.argb(
                  (color.alpha * 255).toInt(),
                  (color.red * 255).toInt(),
                  (color.green * 255).toInt(),
                  (color.blue * 255).toInt())
          setTextColor(androidColor)
        }
      },
      update = { textView -> setHtmlToText(textView, html) })
}

/** Helper function to set HTML content to TextView with proper API level handling. */
private fun setHtmlToText(view: TextView, html: String) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    view.text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
  } else {
    @Suppress("DEPRECATION") view.text = Html.fromHtml(html)
  }
}
