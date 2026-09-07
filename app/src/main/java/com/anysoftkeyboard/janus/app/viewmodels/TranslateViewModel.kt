package com.anysoftkeyboard.janus.app.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anysoftkeyboard.janus.app.repository.OptionalSourceTerm
import com.anysoftkeyboard.janus.app.repository.RecentLanguagesRepository
import com.anysoftkeyboard.janus.app.repository.RelatedArticle
import com.anysoftkeyboard.janus.app.repository.TranslationRepository
import com.anysoftkeyboard.janus.app.util.DetectionResult
import com.anysoftkeyboard.janus.app.util.LanguageDetector
import com.anysoftkeyboard.janus.app.util.StringProvider
import com.anysoftkeyboard.janus.app.util.TranslationFlowMessages
import com.anysoftkeyboard.janus.app.util.TranslationFlowMessagesProvider
import com.anysoftkeyboard.janus.database.entities.Translation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TranslationState() {
  object Translating : TranslationState()

  data class Translated(val translation: Translation) : TranslationState()

  data class MissingTranslation(
      val missingLang: String,
      val availableTranslations: List<Translation>,
  ) : TranslationState()

  data class Error(val errorMessage: String) : TranslationState()
}

sealed interface RelatedArticlesState {
  data object Hidden : RelatedArticlesState

  data object Loading : RelatedArticlesState

  data class Loaded(val articles: List<RelatedArticle>) : RelatedArticlesState
}

sealed class TranslateViewState() {
  object Empty : TranslateViewState()

  object FetchingOptions : TranslateViewState()

  object Detecting : TranslateViewState()

  data class OptionsFetched(
      val searchTerm: String,
      val options: List<OptionalSourceTerm>,
      val translations: Map<OptionalSourceTerm, TranslationState>,
      val effectiveSourceLang: String,
  ) : TranslateViewState()

  data class Translating(
      val searchTerm: String,
      val options: List<OptionalSourceTerm>,
      val selectedTerm: OptionalSourceTerm,
      val sourceLang: String,
      val targetLang: String,
  ) : TranslateViewState()

  data class Translated(
      val term: OptionalSourceTerm,
      val sourceLang: String,
      val targetLang: String,
      val translation: TranslationState,
  ) : TranslateViewState()

  data class Error(val errorType: TranslateViewModel.ErrorType, val errorMessage: String?) :
      TranslateViewState()

  data class AmbiguousSource(val candidates: List<String>, val originalQuery: String) :
      TranslateViewState()
}

@HiltViewModel
class TranslateViewModel
@Inject
constructor(
    private val repository: TranslationRepository,
    private val recentLanguagesRepository: RecentLanguagesRepository,
    private val stringProvider: StringProvider,
    private val welcomeMessageProvider: TranslationFlowMessagesProvider,
    private val languageDetector: LanguageDetector,
) : ViewModel() {
  val recentLanguages: StateFlow<List<String>> = recentLanguagesRepository.recentLanguages
  val sourceLanguage: StateFlow<String> = recentLanguagesRepository.currentSourceLanguage
  val targetLanguage: StateFlow<String> = recentLanguagesRepository.currentTargetLanguage

  fun updateRecentLanguage(code: String) {
    if (code.isNotEmpty()) {
      recentLanguagesRepository.addRecentLanguage(code)
    }
  }

  fun setSourceLanguage(code: String) {
    recentLanguagesRepository.setSourceLanguage(code)
  }

  fun setTargetLanguage(code: String) {
    recentLanguagesRepository.setTargetLanguage(code)
  }

  enum class ErrorType {
    Network,
    RateLimit,
    NotFound,
    Server,
    Unknown,
    DetectionFailed,
    SafetyViolation,
  }

  private val _state = MutableStateFlow<TranslateViewState>(TranslateViewState.Empty)
  val pageState: StateFlow<TranslateViewState> = _state
  private val _relatedArticles = MutableStateFlow<RelatedArticlesState>(RelatedArticlesState.Hidden)
  val relatedArticles: StateFlow<RelatedArticlesState> = _relatedArticles
  private val relatedCache = mutableMapOf<Pair<String, Long>, List<RelatedArticle>>()
  private var relatedArticlesJob: Job? = null

  private val _welcomeMessage = MutableStateFlow(welcomeMessageProvider.getRandomMessage())
  val welcomeMessage: StateFlow<TranslationFlowMessages> = _welcomeMessage

  // Track previous state for back navigation
  private var previousSearchResults: TranslateViewState.OptionsFetched? = null

  fun searchArticles(sourceLang: String, term: String) {
    if (sourceLang.isEmpty()) {
      Log.w("TranslateViewModel", "searchArticles called with empty sourceLang")
      return
    }
    _state.value = TranslateViewState.FetchingOptions
    viewModelScope.launch {
      try {
        val effectiveSourceLang =
            if (sourceLang == LanguageDetector.AUTO_DETECT_LANGUAGE_CODE) {
              _state.value = TranslateViewState.Detecting
              when (val detection = languageDetector.detect(term)) {
                is DetectionResult.Success -> detection.detectedLanguageCode
                is DetectionResult.Ambiguous -> {
                  _state.value =
                      TranslateViewState.AmbiguousSource(
                          candidates = detection.candidates.map { it.languageCode },
                          originalQuery = term,
                      )
                  return@launch
                }
                DetectionResult.SafetyViolation -> {
                  _state.value =
                      TranslateViewState.Error(
                          ErrorType.SafetyViolation,
                          "Safety violation detected",
                      )
                  return@launch
                }
                DetectionResult.Failure -> {
                  throw Exception("Language detection failed")
                }
              }
            } else {
              sourceLang
            }

        performSearch(effectiveSourceLang, term)
      } catch (e: Exception) {
        Log.e("TranslateViewModel", "Error fetching search results", e)
        val errorType = mapToErrorType(e)
        _state.value = TranslateViewState.Error(errorType, e.message)
      }
    }
  }

  fun resolveAmbiguity(selectedLang: String, originalQuery: String) {
    _state.value = TranslateViewState.FetchingOptions
    viewModelScope.launch {
      try {
        performSearch(selectedLang, originalQuery)
      } catch (e: Exception) {
        Log.e("TranslateViewModel", "Error fetching search results", e)
        val errorType = mapToErrorType(e)
        _state.value = TranslateViewState.Error(errorType, e.message)
      }
    }
  }

  private suspend fun performSearch(sourceLang: String, term: String) {
    cancelRelatedArticlesLoad()
    _relatedArticles.value = RelatedArticlesState.Hidden
    _state.value =
        TranslateViewState.OptionsFetched(
            term,
            repository.searchArticles(sourceLang, term),
            emptyMap(),
            sourceLang,
            // If we are performing a search, it means detection (if any) is resolved
        )
  }

  fun fetchTranslation(
      sources: TranslateViewState.OptionsFetched,
      searchPage: OptionalSourceTerm,
      targetLang: String,
  ) {
    // Save current search results for back navigation
    previousSearchResults = sources

    // Transition to Translating state
    _state.value =
        TranslateViewState.Translating(
            sources.searchTerm,
            sources.options,
            searchPage,
            sources.effectiveSourceLang,
            targetLang,
        )

    viewModelScope.launch {
      try {
        val translations =
            repository.fetchTranslations(searchPage, sources.effectiveSourceLang, targetLang)
        val langTranslation = translations.find { it.targetLangCode == targetLang }
        val translationState =
            if (langTranslation == null) {
              // Target language not available, translations contains all available translations
              TranslationState.MissingTranslation(targetLang, translations)
            } else {
              TranslationState.Translated(langTranslation)
            }
        _state.value =
            TranslateViewState.Translated(
                searchPage,
                sources.effectiveSourceLang,
                targetLang,
                translationState,
            )
        loadRelatedArticles(sources.effectiveSourceLang, searchPage)
      } catch (e: Exception) {
        Log.e("TranslateViewModel", "Error fetching translation", e)
        _relatedArticles.value = RelatedArticlesState.Hidden
        val errorType = mapToErrorType(e)
        _state.value = TranslateViewState.Error(errorType, e.message)
      }
    }
  }

  fun fetchRelatedTranslation(related: RelatedArticle, sourceLang: String, targetLang: String) {
    val term = related.toOptionalSourceTerm()
    val sources =
        previousSearchResults?.let { previous ->
          if (previous.options.any { it.pageid == term.pageid }) {
            previous
          } else {
            previous.copy(options = previous.options + term)
          }
        }
            ?: TranslateViewState.OptionsFetched(
                searchTerm = related.title,
                options = listOf(term),
                translations = emptyMap(),
                effectiveSourceLang = sourceLang,
            )
    fetchTranslation(sources, term, targetLang)
  }

  private fun loadRelatedArticles(sourceLang: String, term: OptionalSourceTerm) {
    cancelRelatedArticlesLoad()
    val key = sourceLang to term.pageid
    relatedCache[key]?.let { cached ->
      _relatedArticles.value =
          if (cached.isEmpty()) {
            RelatedArticlesState.Hidden
          } else {
            RelatedArticlesState.Loaded(cached)
          }
      return
    }
    _relatedArticles.value = RelatedArticlesState.Loading
    relatedArticlesJob =
        viewModelScope.launch {
          try {
            val related = repository.getRelatedArticles(sourceLang, term.title, term.pageid)
            relatedCache[key] = related
            if (isShowingTranslationOf(sourceLang, term.pageid)) {
              _relatedArticles.value =
                  if (related.isEmpty()) {
                    RelatedArticlesState.Hidden
                  } else {
                    RelatedArticlesState.Loaded(related)
                  }
            }
          } catch (e: CancellationException) {
            throw e
          } catch (e: Exception) {
            Log.w("TranslateViewModel", "Error fetching related articles", e)
            if (isShowingTranslationOf(sourceLang, term.pageid)) {
              _relatedArticles.value = RelatedArticlesState.Hidden
            }
          }
        }
  }

  private fun isShowingTranslationOf(sourceLang: String, pageId: Long): Boolean {
    val current = _state.value
    return current is TranslateViewState.Translated &&
        current.sourceLang == sourceLang &&
        current.term.pageid == pageId
  }

  private fun cancelRelatedArticlesLoad() {
    relatedArticlesJob?.cancel()
    relatedArticlesJob = null
  }

  private fun mapToErrorType(e: Exception): ErrorType {
    return when (e) {
      is java.net.UnknownHostException,
      is java.net.SocketTimeoutException,
      is java.net.ConnectException -> ErrorType.Network
      is retrofit2.HttpException -> {
        when (e.code()) {
          429 -> ErrorType.RateLimit
          404 -> ErrorType.NotFound
          in 500..599 -> ErrorType.Server
          else -> ErrorType.Unknown
        }
      }
      else -> ErrorType.Unknown
    }
  }

  /**
   * Navigate back from Translated state to the previous search results. If no previous results
   * exist, clears to Empty state.
   */
  fun backToSearchResults() {
    cancelRelatedArticlesLoad()
    _relatedArticles.value = RelatedArticlesState.Hidden
    previousSearchResults?.let { _state.value = it } ?: run { clearSearch() }
  }

  /** Clear search and return to Empty state. Also clears saved search results. */
  fun clearSearch() {
    cancelRelatedArticlesLoad()
    _state.value = TranslateViewState.Empty
    _relatedArticles.value = RelatedArticlesState.Hidden
    relatedCache.clear()
    previousSearchResults = null
    _welcomeMessage.value = welcomeMessageProvider.getRandomMessage()
  }
}
