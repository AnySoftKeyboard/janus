package com.anysoftkeyboard.janus.app.ui.states

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class EmptyStateTest {

  @get:Rule val composeTestRule = createComposeRule()

  @org.junit.Ignore("Fix Robolectric environment issue")
  @Test
  fun testInitialEmptyStateDisplaysCorrectIconAndMessage() {
    // TODO: Fix Robolectric environment issue (RoboMonitoringInstrumentation.java:101)
    /*
    composeTestRule.setContent {
        InitialEmptyState()
    }

    // Verify that one of the welcome messages is displayed
    val context = ApplicationProvider.getApplicationContext<android.content.Context>()
    val possibleMessages = listOf(
        context.getString(R.string.empty_state_initial),
        context.getString(R.string.empty_state_initial_1),
        context.getString(R.string.empty_state_initial_2),
        context.getString(R.string.empty_state_initial_3),
        context.getString(R.string.empty_state_initial_4)
    )

    var messageFound = false
    for (msg in possibleMessages) {
        try {
            composeTestRule.onNodeWithText(msg).assertIsDisplayed()
            messageFound = true
            break
        } catch (e: AssertionError) {
            // Ignore and try next
        }
    }

    assert(messageFound) { "None of the expected welcome messages were found" }
    */
  }
}
