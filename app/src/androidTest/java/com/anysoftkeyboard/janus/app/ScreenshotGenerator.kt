package com.anysoftkeyboard.janus.app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dropbox.dropshots.Dropshots
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreenshotGenerator {

  @get:Rule val composeTestRule = createAndroidComposeRule<MainActivity>()

  @get:Rule val dropshots = Dropshots()

  @Test
  fun capturePlayStoreScreenshots() {
    // Wait for idle
    composeTestRule.waitForIdle()

    // 1. Empty State
    Thread.sleep(500)
    dropshots.assertSnapshot(name = "1_translate_janus_start")
    Thread.sleep(500)

    // 2. Translate "Janus" (EN -> JP)
    performTranslation("Janus", "English", "en", "Japanese", "ja", "1_translate_janus")

    // 3. Translate "Open Source" (EN -> ES)
    performTranslation("Open Source", "English", "en", "Spanish", "es", "2_translate_open_source")

    // 4. Translate "Marie-Antoinette" (FR -> UA)
    performTranslation("Marie-Antoinette", "French", "fr", "Ukrainian", "uk", "3_translate_marie")

    // 5. History View
    composeTestRule.onNodeWithText("History").performClick()
    composeTestRule.waitForIdle()
    // Wait for history items
    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule.onAllNodesWithText("Filter history").fetchSemanticsNodes().isNotEmpty()
    }
    Thread.sleep(500)
    dropshots.assertSnapshot(name = "4_history")
    Thread.sleep(500)

    // 6. Expanded Item (Click "Open Source")
    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule.onAllNodesWithText("Open source").fetchSemanticsNodes().isNotEmpty()
    }
    composeTestRule.onNodeWithText("Open source").performClick()
    composeTestRule.waitForIdle()
    Thread.sleep(500)
    dropshots.assertSnapshot(name = "5_expanded_history")
    Thread.sleep(500)
  }

  private fun performTranslation(
      text: String,
      sourceName: String,
      sourceCode: String,
      targetName: String,
      targetCode: String,
      snapshotName: String
  ) {
    // Clear search if needed
    try {
      composeTestRule.onNodeWithContentDescription("Clear").performClick()
    } catch (e: AssertionError) {
      // Ignore if not present (empty)
    }

    // Ensure we are on Translate tab
    composeTestRule.onNodeWithText("Translate").performClick()

    // Select Source
    composeTestRule.onNodeWithTag("source_lang_selector").performClick()
    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule
          .onAllNodesWithTag("language_menu_item_$sourceCode")
          .fetchSemanticsNodes()
          .isNotEmpty()
    }
    composeTestRule.onNodeWithTag("language_menu_item_$sourceCode").performClick()
    try {
      composeTestRule.waitUntil(timeoutMillis = 2000) {
        composeTestRule
            .onAllNodesWithTag("language_menu_item_$sourceCode")
            .fetchSemanticsNodes()
            .isEmpty()
      }
    } catch (e: androidx.compose.ui.test.ComposeTimeoutException) {
      // Force dismiss
      Espresso.pressBack()
      composeTestRule.waitUntil(timeoutMillis = 15000) {
        composeTestRule
            .onAllNodesWithTag("language_menu_item_$sourceCode")
            .fetchSemanticsNodes()
            .isEmpty()
      }
    }

    // Select Target
    composeTestRule.onNodeWithTag("target_lang_selector").performClick()
    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule
          .onAllNodesWithTag("language_menu_item_$targetCode")
          .fetchSemanticsNodes()
          .isNotEmpty()
    }
    composeTestRule.onNodeWithTag("language_menu_item_$targetCode").performClick()
    try {
      composeTestRule.waitUntil(timeoutMillis = 2000) {
        composeTestRule
            .onAllNodesWithTag("language_menu_item_$targetCode")
            .fetchSemanticsNodes()
            .isEmpty()
      }
    } catch (e: androidx.compose.ui.test.ComposeTimeoutException) {
      // Force dismiss
      Espresso.pressBack()
      composeTestRule.waitUntil(timeoutMillis = 15000) {
        composeTestRule
            .onAllNodesWithTag("language_menu_item_$targetCode")
            .fetchSemanticsNodes()
            .isEmpty()
      }
    }

    // Input Text
    composeTestRule.onNodeWithTag("search_box").performTextInput(text)
    composeTestRule.onNodeWithTag("search_box").performImeAction()

    // Wait for results
    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule.onAllNodesWithTag("search_result_item").fetchSemanticsNodes().isNotEmpty()
    }

    // Wait for translation to show up (click first result)
    composeTestRule.onAllNodesWithTag("search_result_item").onFirst().performClick()

    // Wait for detail view.
    // We wait for checking the "Open source article" button, which is only available in the
    // TranslationView.
    // If we wait for the text, it will be found immediately in the search box.
    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule
          .onAllNodesWithContentDescription("Open source article")
          .fetchSemanticsNodes()
          .isNotEmpty()
    }

    composeTestRule.waitForIdle()
    Thread.sleep(1500)
    dropshots.assertSnapshot(name = snapshotName)
    Thread.sleep(500)
  }
}
