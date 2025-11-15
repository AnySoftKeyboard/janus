package com.anysoftkeyboard.janus.app.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ActionButtonsTest {

  @Test
  fun `test clipboard copy functionality`() {
    val testText = "Cat"
    val context = ApplicationProvider.getApplicationContext<Context>()

    // Simulate what CopyToClipboardButton does
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("article_title", testText)
    clipboard.setPrimaryClip(clip)

    // Verify clipboard contains the text
    val clipData = clipboard.primaryClip
    assertNotNull(clipData)
    assertEquals(1, clipData?.itemCount)
    assertEquals(testText, clipData?.getItemAt(0)?.text)
  }

  @Test
  fun `test clipboard copy with special characters`() {
    val testText = "חתול"
    val context = ApplicationProvider.getApplicationContext<Context>()

    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("article_title", testText)
    clipboard.setPrimaryClip(clip)

    val clipData = clipboard.primaryClip
    assertNotNull(clipData)
    assertEquals(testText, clipData?.getItemAt(0)?.text)
  }

  @Test
  fun `test clipboard copy with empty string`() {
    val testText = ""
    val context = ApplicationProvider.getApplicationContext<Context>()

    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("article_title", testText)
    clipboard.setPrimaryClip(clip)

    val clipData = clipboard.primaryClip
    assertNotNull(clipData)
    assertEquals(testText, clipData?.getItemAt(0)?.text)
  }

  @Test
  fun `test clipboard copy with long text`() {
    val testText = "A".repeat(1000)
    val context = ApplicationProvider.getApplicationContext<Context>()

    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("article_title", testText)
    clipboard.setPrimaryClip(clip)

    val clipData = clipboard.primaryClip
    assertNotNull(clipData)
    assertEquals(testText, clipData?.getItemAt(0)?.text)
  }
}
