package com.anysoftkeyboard.janus.app.ui.data

import com.anysoftkeyboard.janus.database.entities.Translation
import org.junit.Assert.assertEquals
import org.junit.Test

class UiTranslationTest {

  @Test
  fun `fromTranslation maps all fields correctly`() {
    val translation =
        Translation(
            id = 123,
            sourceWord = "Cat",
            sourceLangCode = "en",
            sourceArticleUrl = "https://en.wikipedia.org/?curid=1",
            sourceShortDescription = "A domestic animal",
            sourceSummary = "Cats are small carnivorous mammals.",
            translatedWord = "חתול",
            targetLangCode = "he",
            targetArticleUrl = "https://he.wikipedia.org/?curid=2",
            targetShortDescription = "חיית בית",
            targetSummary = "חתולים הם יונקים טורפים קטנים.",
            timestamp = 1234567890L,
            isFavorite = true)

    val uiTranslation = UiTranslation.fromTranslation(translation)

    assertEquals("Cat", uiTranslation.sourceWord)
    assertEquals("en", uiTranslation.sourceLang)
    assertEquals("https://en.wikipedia.org/?curid=1", uiTranslation.sourceArticleUrl)
    assertEquals("A domestic animal", uiTranslation.sourceShortDescription)
    assertEquals("Cats are small carnivorous mammals.", uiTranslation.sourceSummary)
    assertEquals("חתול", uiTranslation.targetWord)
    assertEquals("he", uiTranslation.targetLang)
    assertEquals("https://he.wikipedia.org/?curid=2", uiTranslation.targetArticleUrl)
    assertEquals("חיית בית", uiTranslation.targetShortDescription)
    assertEquals("חתולים הם יונקים טורפים קטנים.", uiTranslation.targetSummary)
    assertEquals(true, uiTranslation.isFavorite)
  }

  @Test
  fun `fromTranslation handles null descriptions and summaries`() {
    val translation =
        Translation(
            id = 456,
            sourceWord = "Dog",
            sourceLangCode = "en",
            sourceArticleUrl = "https://en.wikipedia.org/?curid=3",
            sourceShortDescription = null,
            sourceSummary = null,
            translatedWord = "כלב",
            targetLangCode = "he",
            targetArticleUrl = "https://he.wikipedia.org/?curid=4",
            targetShortDescription = null,
            targetSummary = null,
            isFavorite = false)

    val uiTranslation = UiTranslation.fromTranslation(translation)

    assertEquals("Dog", uiTranslation.sourceWord)
    assertEquals("en", uiTranslation.sourceLang)
    assertEquals("https://en.wikipedia.org/?curid=3", uiTranslation.sourceArticleUrl)
    assertEquals(null, uiTranslation.sourceShortDescription)
    assertEquals(null, uiTranslation.sourceSummary)
    assertEquals("כלב", uiTranslation.targetWord)
    assertEquals("he", uiTranslation.targetLang)
    assertEquals("https://he.wikipedia.org/?curid=4", uiTranslation.targetArticleUrl)
    assertEquals(null, uiTranslation.targetShortDescription)
    assertEquals(null, uiTranslation.targetSummary)
    assertEquals(false, uiTranslation.isFavorite)
  }

  @Test
  fun `fromTranslation handles partial null fields`() {
    val translation =
        Translation(
            id = 789,
            sourceWord = "Bird",
            sourceLangCode = "en",
            sourceArticleUrl = "https://en.wikipedia.org/?curid=5",
            sourceShortDescription = "A flying animal",
            sourceSummary = null,
            translatedWord = "ציפור",
            targetLangCode = "he",
            targetArticleUrl = "https://he.wikipedia.org/?curid=6",
            targetShortDescription = null,
            targetSummary = "ציפורים הן בעלי חיים מעופפים.",
            isFavorite = false)

    val uiTranslation = UiTranslation.fromTranslation(translation)

    assertEquals("Bird", uiTranslation.sourceWord)
    assertEquals("A flying animal", uiTranslation.sourceShortDescription)
    assertEquals(null, uiTranslation.sourceSummary)
    assertEquals("ציפור", uiTranslation.targetWord)
    assertEquals(null, uiTranslation.targetShortDescription)
    assertEquals("ציפורים הן בעלי חיים מעופפים.", uiTranslation.targetSummary)
  }

  @Test
  fun `fromTranslation handles empty strings`() {
    val translation =
        Translation(
            id = 101,
            sourceWord = "",
            sourceLangCode = "en",
            sourceArticleUrl = "",
            sourceShortDescription = "",
            sourceSummary = "",
            translatedWord = "",
            targetLangCode = "he",
            targetArticleUrl = "",
            targetShortDescription = "",
            targetSummary = "",
            isFavorite = false)

    val uiTranslation = UiTranslation.fromTranslation(translation)

    assertEquals("", uiTranslation.sourceWord)
    assertEquals("", uiTranslation.sourceArticleUrl)
    assertEquals("", uiTranslation.sourceShortDescription)
    assertEquals("", uiTranslation.sourceSummary)
    assertEquals("", uiTranslation.targetWord)
    assertEquals("", uiTranslation.targetArticleUrl)
    assertEquals("", uiTranslation.targetShortDescription)
    assertEquals("", uiTranslation.targetSummary)
  }

  @Test
  fun testToTranslation() {
    val uiTranslation =
        UiTranslation(
            sourceWord = "Cat",
            sourceLang = "en",
            sourceArticleUrl = "url1",
            sourceShortDescription = "desc1",
            sourceSummary = "summary1",
            targetWord = "Gato",
            targetLang = "es",
            targetArticleUrl = "url2",
            targetShortDescription = "desc2",
            targetSummary = "summary2",
            isFavorite = true,
            timestamp = 1234567890L,
            id = 1)

    val translation = uiTranslation.toTranslation()

    assertEquals(1, translation.id)
    assertEquals("Cat", translation.sourceWord)
    assertEquals("en", translation.sourceLangCode)
    assertEquals("url1", translation.sourceArticleUrl)
    assertEquals("desc1", translation.sourceShortDescription)
    assertEquals("summary1", translation.sourceSummary)
    assertEquals("Gato", translation.translatedWord)
    assertEquals("es", translation.targetLangCode)
    assertEquals("url2", translation.targetArticleUrl)
    assertEquals("desc2", translation.targetShortDescription)
    assertEquals("summary2", translation.targetSummary)
    assertEquals(true, translation.isFavorite)
    assertEquals(1234567890L, translation.timestamp)
  }
}
