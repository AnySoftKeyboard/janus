package com.anysoftkeyboard.janus.app.util

import android.util.Log
import com.google.mlkit.genai.prompt.Generation
import com.google.mlkit.genai.prompt.GenerativeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiLanguageDetector @Inject constructor(private val fallback: OptimaizeLanguageDetector) :
    LanguageDetector {

  private val model: GenerativeModel by lazy { Generation.getClient() }

  override suspend fun detect(text: String): DetectionResult {
    val result =
        try {
          detectWithGemini(text)
        } catch (e: Exception) {
          Log.e("GeminiDetector", "Error detecting language with Gemini, falling back", e)
          DetectionResult.Failure
        }

    if (result is DetectionResult.Failure || result is DetectionResult.SafetyViolation) {
      Log.d("GeminiDetector", "Gemini failed ($result), trying fallback")
      return fallback.detect(text)
    }
    return result
  }

  private suspend fun detectWithGemini(text: String): DetectionResult {
    // Request format: "code:confidence" separated by commas
    val prompt =
        """
          Analyze the following text and determine its language.
          The text might be ambiguous (valid in multiple languages).
          Return a list of 1 to 3 logical languages with a confidence score (0.0 to 1.0) for each.
          Format: [2-letter-code]:[confidence]
          Output examples:
          - en:1.0
          - es:0.9, en:0.7
          - fr:0.8, en:0.75, es:0.1
          - he:0.95, ar:0.05

          Text: "$text"
          """
            .trimIndent()

    val response = model.generateContent(prompt)
    val resultText = response.candidates.firstOrNull()?.text?.trim() ?: ""
    Log.d("GeminiDetector", "Input: '$text' -> Raw: '$resultText'")

    if (resultText.lowercase().contains("unknown") || resultText.isEmpty()) {
      return DetectionResult.Failure
    }

    // Cleanup: remove any markdown list characters if present (e.g. "- ") or newlines
    val cleanText = resultText.replace("\n", ",").replace("-", "").trim()

    val candidates =
        cleanText
            .split(",")
            .mapNotNull {
              val parts = it.split(":")
              if (parts.size == 2) {
                val code = parts[0].trim().lowercase()
                val confidence = parts[1].trim().toFloatOrNull() ?: 0f
                if (confidence > 0 && code.length == 2) {
                  DetectionResult.Ambiguous.Candidate(code, confidence)
                } else null
              } else null
            }
            .sortedByDescending { it.confidence }

    return when {
      candidates.isEmpty() -> DetectionResult.Failure
      candidates.size == 1 ->
          DetectionResult.Success(candidates[0].languageCode, candidates[0].confidence)
      else -> {
        val first = candidates[0]
        val second = candidates[1]
        // If first is significantly better, use it. Otherwise ambiguous.
        if (first.confidence > 0.8f && (first.confidence - second.confidence) > 0.3f) {
          DetectionResult.Success(first.languageCode, first.confidence)
        } else {
          // Return top 3 as ambiguous
          DetectionResult.Ambiguous(candidates.take(3))
        }
      }
    }
  }
}
