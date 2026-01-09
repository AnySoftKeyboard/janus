package com.anysoftkeyboard.janus.app.util

import android.util.Log
import com.google.mlkit.genai.prompt.Generation
import com.google.mlkit.genai.prompt.GenerativeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiLanguageDetector @Inject constructor() : LanguageDetector {

  private val model: GenerativeModel by lazy { Generation.getClient() }

  override suspend fun detect(text: String): DetectionResult {
    return try {
      val prompt =
          "Detect the language of the following text. Return ONLY the ISO 639-1 language code (e.g., 'en', 'fr', 'es'). If unknown or mixed, return 'unknown'. Text: \"$text\""

      val response = model.generateContent(prompt)
      val resultText = response.candidates.firstOrNull()?.text?.trim()?.lowercase() ?: "unknown"
      Log.d("GeminiDetector", "Input: '$text' -> Detected: '$resultText'")

      if (resultText == "unknown" || resultText.length > 5) { // Sanity check length
        DetectionResult.Failure
      } else {
        DetectionResult.Success(resultText, 1.0f)
      }
    } catch (e: Exception) {
      Log.e("GeminiDetector", "Error detecting language", e)
      DetectionResult.Failure
    }
  }
}
