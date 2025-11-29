package com.anysoftkeyboard.janus.app.ui.items

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HistoryItemTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val testTranslation = UiTranslation(
      sourceWord = "Cat",
      sourceLang = "en",
      sourceArticleUrl = "http://en.wikipedia.org/wiki/Cat",
      sourceShortDescription = "A small domesticated carnivorous mammal.",
      sourceSummary = "The cat (Felis catus) is a domestic species of small carnivorous mammal.",
      targetWord = "Gato",
      targetLang = "es",
      targetArticleUrl = "http://es.wikipedia.org/wiki/Gato",
      targetShortDescription = "Mamífero carnívoro de la familia Felidae.",
      targetSummary = "El gato doméstico (Felis silvestris catus), llamado popularmente gato...",
      isFavorite = false,
      timestamp = 1234567890L
  )

  @OptIn(ExperimentalSharedTransitionApi::class)
  @Test
  fun testHistoryItemRenders() {
    composeTestRule.setContent {
      SharedTransitionLayout {
        HistoryItem(
            translation = testTranslation,
            isExpanded = false,
            unfocused = false,
            sharedTransitionScope = this
        )
      }
    }

    composeTestRule.onNodeWithText("Cat").assertExists()
    composeTestRule.onNodeWithText("Gato").assertExists()
  }

  @OptIn(ExperimentalSharedTransitionApi::class)
  @Test
  fun testHistoryItemRendersUnfocused() {
     composeTestRule.setContent {
      SharedTransitionLayout {
        HistoryItem(
            translation = testTranslation,
            isExpanded = false,
            unfocused = true,
            sharedTransitionScope = this
        )
      }
    }

    composeTestRule.onNodeWithText("Cat").assertExists()
  }
}
