package com.anysoftkeyboard.janus.app

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun translateTabIsSelectedByDefault() {
    composeTestRule.setContent { JanusApp() }
    composeTestRule.onNodeWithText("Translate").assertIsSelected()
  }

  @Test
  fun clickingHistoryTabNavigatesToHistoryScreenAndSelectsTab() {
    composeTestRule.setContent { JanusApp() }
    composeTestRule.onNodeWithText("History").performClick()
    composeTestRule.onNodeWithText("History").assertIsSelected()
    composeTestRule.onNodeWithText("History Screen Content").assertExists()
  }

  @Test
  fun translateScreenContentIsDisplayed() {
    // This is a placeholder test
    composeTestRule.setContent { TranslateScreen() }
    composeTestRule.onNodeWithText("Translate Screen Content").assertExists()
  }

  @Test
  fun historyScreenContentIsDisplayed() {
    // This is a placeholder test
    composeTestRule.setContent { HistoryScreen() }
    composeTestRule.onNodeWithText("History Screen Content").assertExists()
  }

  @Test
  fun bookmarksScreenContentIsDisplayed() {
    // This is a placeholder test
    composeTestRule.setContent { BookmarksScreen() }
    composeTestRule.onNodeWithText("Bookmarks Screen Content").assertExists()
  }
}
