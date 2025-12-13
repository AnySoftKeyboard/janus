package com.anysoftkeyboard.janus.app

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TranslationFlowTest {

  @get:Rule val composeTestRule = createAndroidComposeRule<MainActivity>()

  @Test
  fun testTranslationFlow() {
    // Wait for the app to be idle and displayed
    composeTestRule.waitForIdle()

    // 1. translation user picks the source language "English"
    // Open source language selector
    composeTestRule.onNodeWithTag("source_lang_selector").performClick()
    // Select English
    composeTestRule.onNodeWithTag("language_menu_item_en").performClick()

    // 2. user picks the target language "Spanish"
    // Open target language selector
    composeTestRule.onNodeWithTag("target_lang_selector").performClick()
    // Select Spanish
    composeTestRule.onNodeWithTag("language_menu_item_es").performClick()

    // 3. verify the selections are in the UI
    composeTestRule.onNodeWithTag("source_lang_selector").assertIsDisplayed()
    composeTestRule.onNodeWithText("English").assertIsDisplayed()
    composeTestRule.onNodeWithTag("target_lang_selector").assertIsDisplayed()
    composeTestRule.onNodeWithText("Spanish").assertIsDisplayed()

    // 4. search for "Bluey"
    composeTestRule.onNodeWithTag("search_box").performTextInput("Bluey")
    composeTestRule.onNodeWithTag("search_box").performImeAction()

    // 5. verify results
    composeTestRule.waitUntil(timeoutMillis = 5000) {
      composeTestRule.onAllNodesWithTag("search_result_item").fetchSemanticsNodes().isNotEmpty()
    }

    // Assert specifically for results, but now we fail faster if error shows up
    composeTestRule
        .onAllNodesWithTag("search_result_item")
        .onFirst()
        .assert(hasText("Bluey (TV series)"))

    // 6. Click on the first result and verify translation view
    composeTestRule.onAllNodesWithTag("search_result_item").onFirst().performClick()

    composeTestRule.waitUntil(timeoutMillis = 5000) {
      composeTestRule.onAllNodesWithText("EN").fetchSemanticsNodes().isNotEmpty()
    }

    // Verify Source (English at the top)
    composeTestRule.onNodeWithText("EN").assertIsDisplayed()
    composeTestRule.onNodeWithText("Bluey (TV series)").assertIsDisplayed()

    // Verify Target (Spanish at the bottom)
    composeTestRule.onNodeWithText("ES").assertIsDisplayed()
    composeTestRule.onNodeWithText("Bluey (serie de televisión)").assertIsDisplayed()

    // 7. Clear search and search for "the big apple"
    composeTestRule.onNodeWithContentDescription("Clear").performClick()
    composeTestRule.onNodeWithTag("search_box").assert(hasText("Word to translate"))

    composeTestRule.onNodeWithTag("search_box").performTextInput("the big apple")
    composeTestRule.onNodeWithTag("search_box").performImeAction()

    // 8. Verify items is "Big Apple"
    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule.onAllNodesWithTag("search_result_item").fetchSemanticsNodes().isNotEmpty()
    }

    composeTestRule.onAllNodesWithTag("search_result_item").onFirst().assert(hasText("Big Apple"))

    // 9. Click and verify translation
    composeTestRule.onAllNodesWithTag("search_result_item").onFirst().performClick()

    composeTestRule.waitUntil(timeoutMillis = 15000) {
      composeTestRule.onAllNodesWithText("La Gran Manzana").fetchSemanticsNodes().isNotEmpty()
    }

    // Verify Source
    composeTestRule.onNodeWithText("Big Apple").assertIsDisplayed()

    // Verify Target
    composeTestRule.onNodeWithText("La Gran Manzana").assertIsDisplayed()

    // 10. Go to History tab
    composeTestRule.onNodeWithText("History").performClick()

    // 11. Verify History content
    composeTestRule.waitUntil(timeoutMillis = 5000) {
      composeTestRule.onAllNodesWithText("Filter history").fetchSemanticsNodes().isNotEmpty()
    }

    // Verify Big Apple
    composeTestRule.onNodeWithText("Big Apple").assertIsDisplayed()
    composeTestRule.onNodeWithText("La Gran Manzana").assertIsDisplayed()

    // Verify Bluey
    composeTestRule.onNodeWithText("Bluey (TV series)").assertIsDisplayed()
    composeTestRule.onNodeWithText("Bluey (serie de televisión)").assertIsDisplayed()

    // 12. Click on Big Apple to expand
    composeTestRule.onNodeWithText("Big Apple").performClick()

    composeTestRule.waitUntil(timeoutMillis = 5000) {
      composeTestRule.onAllNodesWithText("EN").fetchSemanticsNodes().isNotEmpty()
    }

    // Verify expanded content (Language codes are visible)
    composeTestRule.onNodeWithText("EN").assertIsDisplayed()
    composeTestRule.onNodeWithText("ES").assertIsDisplayed()
  }

  @Test
  fun testLanguageSelectionPersistsOnRecreation() {
    // Wait for the app to be idle and displayed
    composeTestRule.waitForIdle()

    // 1. Verify initial selection is "English" and "Hebrew" (default)
    composeTestRule.onNodeWithText("English").assertIsDisplayed()
    composeTestRule.onNodeWithText("Hebrew").assertIsDisplayed()

    // 2. Change selection to "Spanish" and "French"
    // Open source language selector
    composeTestRule.onNodeWithTag("source_lang_selector").performClick()
    // Select Spanish
    composeTestRule.onNodeWithTag("language_menu_item_es").performClick()

    // Open target language selector
    composeTestRule.onNodeWithTag("target_lang_selector").performClick()
    // Select French
    composeTestRule.onNodeWithTag("language_menu_item_fr").performClick()

    // Verify selection updated
    composeTestRule.onNodeWithText("Spanish").assertIsDisplayed()
    composeTestRule.onNodeWithText("French").assertIsDisplayed()

    // 3. Trigger activity recreation
    composeTestRule.activityRule.scenario.recreate()
    composeTestRule.waitForIdle()

    // 4. Verify selection persists (is still "Spanish" and "French")
    composeTestRule.onNodeWithText("Spanish").assertIsDisplayed()
    composeTestRule.onNodeWithText("French").assertIsDisplayed()
  }
}
