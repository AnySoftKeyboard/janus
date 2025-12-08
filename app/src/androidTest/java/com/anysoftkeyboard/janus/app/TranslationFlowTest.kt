package com.anysoftkeyboard.janus.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
  }
}
