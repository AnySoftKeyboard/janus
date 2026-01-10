package com.anysoftkeyboard.janus.app

import androidx.compose.ui.test.hasTestTag
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
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
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
    dropshots.assertSnapshot(name = "a_1_translate_janus_start")
    Thread.sleep(500)

    // 2. Translate "Janus" (EN -> JP)
    performTranslation("Janus", "English", "en", "Japanese", "ja", "a_2_translate_janus")

    // 3. Translate "Open Source" (EN -> ES)
    performTranslation("Open Source", "English", "en", "Spanish", "es", "a_3_translate_open_source")

    // 4. Translate "Marie-Antoinette" (FR -> UA)
    performTranslation("Marie-Antoinette", "French", "fr", "Ukrainian", "uk", "a_4_translate_marie")

    // 5. History View
    composeTestRule.onNodeWithText("History").performClick()
    composeTestRule.waitForIdle()
    // Wait for history items
    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule.onAllNodesWithText("Filter history").fetchSemanticsNodes().isNotEmpty()
    }
    Thread.sleep(500)
    dropshots.assertSnapshot(name = "a_5_history")
    Thread.sleep(500)

    // 6. Expanded Item (Click "Open Source")
    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule.onAllNodesWithText("Open source").fetchSemanticsNodes().isNotEmpty()
    }
    composeTestRule.onNodeWithText("Open source").performClick()
    composeTestRule.waitForIdle()
    Thread.sleep(500)
    dropshots.assertSnapshot(name = "a_6_expanded_history")
    Thread.sleep(500)
  }

  //  @Test
  fun screenshotAutoDetectAndAmbiguous() {
    composeTestRule.waitForIdle()

    // 1. Open Source Selector
    composeTestRule.onNodeWithTag("source_lang_selector").performClick()
    composeTestRule
        .onNodeWithTag("language_list")
        .performScrollToNode(androidx.compose.ui.test.hasTestTag("language_menu_item_auto"))
    Thread.sleep(500)
    dropshots.assertSnapshot(name = "b_1_auto_detect_option")

    // 2. Select Auto-detect
    composeTestRule.onNodeWithTag("language_menu_item_auto").performClick()
    composeTestRule.waitForIdle()

    // 3. Enter "pan" and search
    composeTestRule.onNodeWithTag("search_box").performTextInput("pan")
    composeTestRule.onNodeWithTag("search_box").performImeAction()

    // 4. Wait for Ambiguous Dialog
    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule
          .onAllNodesWithText("Which language is this?")
          .fetchSemanticsNodes()
          .isNotEmpty()
    }
    Thread.sleep(500)
    dropshots.assertSnapshot(name = "b_2_ambiguous_dialog")
  }

  private fun performTranslation(
      text: String,
      sourceName: String,
      sourceCode: String,
      targetName: String,
      targetCode: String,
      snapshotName: String,
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
    composeTestRule
        .onNodeWithTag("language_list")
        .performScrollToNode(hasTestTag("language_menu_item_$sourceCode"))
    composeTestRule.onAllNodesWithTag("language_menu_item_$sourceCode").onFirst().performClick()

    // Select Target
    composeTestRule.onNodeWithTag("target_lang_selector").performClick()
    composeTestRule
        .onNodeWithTag("language_list")
        .performScrollToNode(hasTestTag("language_menu_item_$targetCode"))
    composeTestRule.onAllNodesWithTag("language_menu_item_$targetCode").onFirst().performClick()

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
