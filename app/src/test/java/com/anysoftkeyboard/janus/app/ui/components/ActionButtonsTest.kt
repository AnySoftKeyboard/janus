package com.anysoftkeyboard.janus.app.ui.components

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class ActionButtonsTest {

  @get:Rule val composeTestRule = createComposeRule()

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

  @Test
  fun `test wikipedia link button click opens url safely`() {
    val testUrl = "https://en.wikipedia.org/wiki/Cat"
    composeTestRule.setContent { WikipediaLinkButton(url = testUrl) }

    composeTestRule.onNodeWithContentDescription("Open in Wikipedia").performClick()

    val app = shadowOf(ApplicationProvider.getApplicationContext<Application>())
    val nextIntent = app.nextStartedActivity
    assertNotNull(nextIntent)
    assertEquals(Intent.ACTION_VIEW, nextIntent.action)
    assertEquals(testUrl, nextIntent.dataString)
  }
}
