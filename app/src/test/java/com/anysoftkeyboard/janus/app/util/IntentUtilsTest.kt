package com.anysoftkeyboard.janus.app.util

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import com.anysoftkeyboard.janus.app.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
class IntentUtilsTest {

  private val context: Context
    get() = ApplicationProvider.getApplicationContext()

  @Test
  fun testOpenUrlSafely_success_startsActionViewIntent() {
    val testUrl = "https://example.com/test"
    context.openUrlSafely(testUrl)

    val app = shadowOf(ApplicationProvider.getApplicationContext<Application>())
    val nextIntent = app.nextStartedActivity
    assertNotNull(nextIntent)
    assertEquals(Intent.ACTION_VIEW, nextIntent.action)
    assertEquals(testUrl, nextIntent.dataString)
  }

  @Test
  fun testOpenUrlSafely_activityNotFound_copiesToClipboardAndShowsToast() {
    val failingContext =
        object : ContextWrapper(context) {
          override fun startActivity(intent: Intent?) {
            throw ActivityNotFoundException("No activity found")
          }
        }

    val testUrl = "https://example.com/fallback"
    failingContext.openUrlSafely(testUrl)

    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = clipboard.primaryClip
    assertNotNull(clip)
    assertEquals(testUrl, clip?.getItemAt(0)?.text?.toString())

    val expectedToastText = context.getString(R.string.error_no_browser)
    assertEquals(expectedToastText, ShadowToast.getTextOfLatestToast())
  }

  @Test
  fun testOpenUrlSafely_genericException_copiesToClipboardAndShowsToast() {
    val failingContext =
        object : ContextWrapper(context) {
          override fun startActivity(intent: Intent?) {
            throw SecurityException("Locked device")
          }
        }

    val testUrl = "https://example.com/secure"
    failingContext.openUrlSafely(testUrl)

    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = clipboard.primaryClip
    assertNotNull(clip)
    assertEquals(testUrl, clip?.getItemAt(0)?.text?.toString())

    val expectedToastText = context.getString(R.string.error_no_browser)
    assertEquals(expectedToastText, ShadowToast.getTextOfLatestToast())
  }
}
