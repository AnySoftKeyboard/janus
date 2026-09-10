package com.anysoftkeyboard.janus.app.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.anysoftkeyboard.janus.app.R

private const val TAG = "IntentUtils"

/**
 * Safely opens a web URL using [Intent.ACTION_VIEW].
 *
 * If no browser or handler is available (e.g. headless device, kiosk mode, or restricted profile),
 * catches [ActivityNotFoundException] or other launch exceptions, logs a warning, copies the URL to
 * the clipboard, and displays a user-facing [Toast].
 */
fun Context.openUrlSafely(url: String) {
  val intent =
      Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        if (this@openUrlSafely !is Activity) {
          addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
      }
  try {
    startActivity(intent)
  } catch (e: ActivityNotFoundException) {
    Log.w(TAG, "No activity found to handle URL: $url", e)
    handleOpenUrlFailure(url)
  } catch (e: Exception) {
    Log.e(TAG, "Failed to open URL: $url", e)
    handleOpenUrlFailure(url)
  }
}

private fun Context.handleOpenUrlFailure(url: String) {
  try {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    clipboard?.setPrimaryClip(ClipData.newPlainText("url", url))
  } catch (e: Exception) {
    Log.w(TAG, "Failed to copy URL to clipboard", e)
  }
  Toast.makeText(this, R.string.error_no_browser, Toast.LENGTH_LONG).show()
}
