package com.anysoftkeyboard.janus.app.util

import javax.inject.Inject

sealed class DetectionResult {
  data class Success(val detectedLanguageCode: String, val confidence: Float) : DetectionResult()

  data class Ambiguous(val candidates: List<Candidate>) : DetectionResult() {
    data class Candidate(val languageCode: String, val confidence: Float)
  }

  object Failure : DetectionResult()
}

interface LanguageDetector {
  companion object {
    const val AUTO_DETECT_LANGUAGE_CODE = "auto"
  }

  suspend fun detect(text: String): DetectionResult
}

class MockLanguageDetector @Inject constructor() : LanguageDetector {
  override suspend fun detect(text: String): DetectionResult {
    // Simple mock: always return English unless the text is specifically "hola" (es) or
    // "bonjour" (fr) for testing.
    return when (text.lowercase().trim()) {
      "hola" -> DetectionResult.Success("es", 0.9f)
      "bonjour" -> DetectionResult.Success("fr", 0.9f)
      "shalom" -> DetectionResult.Success("he", 0.9f)
      "fail" -> DetectionResult.Failure
      "ambiguous" ->
          DetectionResult.Ambiguous(
              listOf(
                  DetectionResult.Ambiguous.Candidate("en", 0.6f),
                  DetectionResult.Ambiguous.Candidate("fr", 0.35f),
              )
          )
      else -> DetectionResult.Success("en", 0.8f)
    }
  }
}
