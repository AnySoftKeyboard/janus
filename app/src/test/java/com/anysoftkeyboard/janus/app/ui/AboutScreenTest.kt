package com.anysoftkeyboard.janus.app.ui

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.core.app.ApplicationProvider
import com.anysoftkeyboard.janus.app.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AboutScreenTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val context: Context
    get() = ApplicationProvider.getApplicationContext()

  @Test
  fun testAboutScreen_displaysAppInfoAndTitle() {
    composeTestRule.setContent { AboutScreen() }

    val expectedTitle = context.getString(R.string.about_title)
    composeTestRule.onNodeWithText(expectedTitle).assertIsDisplayed()

    val expectedLicensesButton = context.getString(R.string.about_button_licenses)
    composeTestRule.onNodeWithText(expectedLicensesButton).performScrollTo().assertIsDisplayed()
  }

  @Test
  fun testAboutScreen_clickLicensesButton_opensLicensesView() {
    composeTestRule.setContent { AboutScreen() }

    val licensesButtonText = context.getString(R.string.about_button_licenses)
    composeTestRule.onNodeWithText(licensesButtonText).performScrollTo().performClick()

    // When clicked, the licenses view is shown with the title in the top bar
    composeTestRule.onNodeWithText(licensesButtonText).assertIsDisplayed()
  }
}
