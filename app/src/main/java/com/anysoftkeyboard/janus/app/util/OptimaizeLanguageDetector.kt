package com.anysoftkeyboard.janus.app.util

import android.util.Log
import com.optimaize.langdetect.LanguageDetector
import com.optimaize.langdetect.LanguageDetectorBuilder
import com.optimaize.langdetect.ngram.NgramExtractors
import com.optimaize.langdetect.profiles.LanguageProfileReader
import com.optimaize.langdetect.text.CommonTextObjectFactories
import com.optimaize.langdetect.text.TextObjectFactory
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class OptimaizeLanguageDetector @Inject constructor() :
    com.anysoftkeyboard.janus.app.util.LanguageDetector {

  private val detector: LanguageDetector? by lazy {
    try {
      // Load all built-in profiles (covers ~70 languages)
      val languageProfiles = LanguageProfileReader().readAllBuiltIn()
      LanguageDetectorBuilder.create(NgramExtractors.standard())
          .withProfiles(languageProfiles)
          .build()
    } catch (e: Exception) {
      Log.e("OptimaizeDetector", "Failed to initialize detector", e)
      null
    }
  }

  // Factory for short texts (e.g. search queries)
  private val shortTextFactory: TextObjectFactory =
      CommonTextObjectFactories.forDetectingShortCleanText()
  // Factory for longer texts
  private val largeTextFactory: TextObjectFactory =
      CommonTextObjectFactories.forDetectingOnLargeText()

  override suspend fun detect(text: String): DetectionResult =
      withContext(Dispatchers.Default) {
        if (text.isBlank()) return@withContext DetectionResult.Failure

        val det = detector ?: return@withContext DetectionResult.Failure

        try {
          // Choose factory based on length (heuristic)
          val factory = if (text.length < 100) shortTextFactory else largeTextFactory
          val textObject = factory.forText(text)

          val probabilities = det.getProbabilities(textObject)

          if (probabilities.isEmpty()) return@withContext DetectionResult.Failure

          // Convert to our format
          val candidates =
              probabilities.map {
                DetectionResult.Ambiguous.Candidate(it.locale.language, it.probability.toFloat())
              }

          val top = candidates.first()
          // If confidence is high enough (e.g. > 0.8), return Success.
          if (top.confidence > 0.8f) {
            DetectionResult.Success(top.languageCode, top.confidence)
          } else {
            DetectionResult.Ambiguous(candidates)
          }
        } catch (e: Exception) {
          Log.e("OptimaizeDetector", "Error detecting language", e)
          DetectionResult.Failure
        }
      }
}
