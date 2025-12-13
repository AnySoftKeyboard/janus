package com.anysoftkeyboard.janus.app

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows

@RunWith(AndroidJUnit4::class)
class ProcessTextActivityTest {

  @Test
  fun testProcessTextForwarding() {
    val text = "Hello World"
    val intent =
        Intent(ApplicationProvider.getApplicationContext(), ProcessTextActivity::class.java).apply {
          action = Intent.ACTION_PROCESS_TEXT
          putExtra(Intent.EXTRA_PROCESS_TEXT, text)
          type = "text/plain"
        }

    ActivityScenario.launch<ProcessTextActivity>(intent).use {
      // Activity finishes in onCreate, so we check ShadowApplication directly
      val app =
          Shadows.shadowOf(ApplicationProvider.getApplicationContext<android.app.Application>())
      val nextIntent = app.nextStartedActivity

      if (nextIntent == null) {
        throw AssertionError("nextIntent should not be null. Activity logs should explain why.")
      }
      assertEquals(MainActivity::class.java.name, nextIntent.component?.className)
      assertEquals(text, nextIntent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT))
      assertEquals(
          Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP,
          nextIntent.flags and (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
  }

  @Test
  fun testProcessTextWithHtmlMimeTypeForwardsPlainText() {
    val text = "<b>Hello</b> World"
    val intent =
        Intent(ApplicationProvider.getApplicationContext(), ProcessTextActivity::class.java).apply {
          action = Intent.ACTION_PROCESS_TEXT
          putExtra(Intent.EXTRA_PROCESS_TEXT, text)
          type = "text/html"
        }

    ActivityScenario.launch<ProcessTextActivity>(intent).use {
      val app =
          Shadows.shadowOf(ApplicationProvider.getApplicationContext<android.app.Application>())
      val nextIntent = app.nextStartedActivity

      if (nextIntent == null) {
        throw AssertionError("nextIntent should not be null. Activity logs should explain why.")
      }
      assertEquals(MainActivity::class.java.name, nextIntent.component?.className)
      assertEquals(text, nextIntent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT))
      assertEquals(
          Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP,
          nextIntent.flags and (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
  }

  @Test
  fun testProcessTextWithNoTextDoesNotStartActivity() {
    val intent =
        Intent(ApplicationProvider.getApplicationContext(), ProcessTextActivity::class.java).apply {
          action = Intent.ACTION_PROCESS_TEXT
          type = "text/plain"
        }

    ActivityScenario.launch<ProcessTextActivity>(intent).use {
      val app =
          Shadows.shadowOf(ApplicationProvider.getApplicationContext<android.app.Application>())
      val nextIntent = app.nextStartedActivity
      assertEquals(null, nextIntent)
    }
  }

  @Test
  fun testProcessTextWithSpannableExtra() {
    val text = android.text.SpannableString("Hello World")
    text.setSpan(
        android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
        0,
        5,
        android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE)

    val intent =
        Intent(ApplicationProvider.getApplicationContext(), ProcessTextActivity::class.java).apply {
          action = Intent.ACTION_PROCESS_TEXT
          putExtra(Intent.EXTRA_PROCESS_TEXT, text)
          type = "text/plain"
        }

    ActivityScenario.launch<ProcessTextActivity>(intent).use {
      val app =
          Shadows.shadowOf(ApplicationProvider.getApplicationContext<android.app.Application>())
      val nextIntent = app.nextStartedActivity

      if (nextIntent == null) {
        throw AssertionError("nextIntent should not be null. Activity logs should explain why.")
      }
      assertEquals(MainActivity::class.java.name, nextIntent.component?.className)
      // Verify content is preserved
      assertEquals(
          "Hello World", nextIntent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString())
    }
  }
}
