package com.anysoftkeyboard.janus.app.util

import android.util.Log
import com.google.mlkit.genai.prompt.PromptApi
import com.google.mlkit.genai.prompt.PromptModel
import com.google.mlkit.genai.prompt.PromptOptions
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

@Singleton
class GeminiLanguageDetector @Inject constructor() : LanguageDetector {

  private var promptModel: PromptModel? = null

  override suspend fun detect(text: String): DetectionResult {
    return try {
      val model = getOrInitializeModel()
      // Create the prompt with the input text
      val prompt =
          "Detect the language of the following text. Return ONLY the ISO 639-1 language code (e.g., 'en', 'fr', 'es'). If unknown or mixed, return 'unknown'. Text: \"$text\""

      val resultText = model.generate(prompt).text?.trim()?.lowercase() ?: "unknown"
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

  private suspend fun getOrInitializeModel(): PromptModel {
    promptModel?.let {
      return it
    }

    return suspendCancellableCoroutine { continuation ->
      val options = PromptOptions.Builder().build()
      PromptApi.getClient()
          .checkAvailability()
          .addOnSuccessListener { availability ->
            if (availability == PromptApi.Availability.AVAILABLE) {
              PromptApi.getClient()
                  .load(options)
                  .addOnSuccessListener { model ->
                    promptModel = model
                    continuation.resume(model)
                  }
                  .addOnFailureListener { e -> continuation.resumeWithException(e) }
            } else {
              // Handle unavailable (download required, device not supported, etc.)
              // For now, fail. Ideally trigger download.
              continuation.resumeWithException(
                  Exception("Gemini Nano not available: $availability")
              )
            }
          }
          .addOnFailureListener { e -> continuation.resumeWithException(e) }
    }
  }
}
