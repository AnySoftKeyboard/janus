package com.anysoftkeyboard.janus.app.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.repository.OptionalSourceTerm
import com.anysoftkeyboard.janus.app.repository.TranslationRepository
import com.anysoftkeyboard.janus.app.util.StringProvider
import com.anysoftkeyboard.janus.database.entities.Translation
import com.anysoftkeyboard.janus.app.util.TranslationFlowMessages
import com.anysoftkeyboard.janus.app.util.WelcomeMessageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TranslationState() {
  object Translating : TranslationState()

  data class Translated(val translation: Translation) : TranslationState()

  data class MissingTranslation(
      val missingLang: String,
      val availableTranslations: List<Translation>
  ) : TranslationState()

  data class Error(val errorMessage: String) : TranslationState()
}

sealed class TranslateViewState() {
  object Empty : TranslateViewState()

  object FetchingOptions : TranslateViewState()

  data class OptionsFetched(
      val searchTerm: String,
      val options: List<OptionalSourceTerm>,
      val translations: Map<OptionalSourceTerm, TranslationState>
  ) : TranslateViewState()

  data class Translated(
      val term: OptionalSourceTerm,
      val sourceLang: String,
      val targetLang: String,
      val translation: TranslationState
  ) : TranslateViewState()

  data class Error(val errorType: String, val errorMessage: String) : TranslateViewState()
}

@HiltViewModel
class TranslateViewModel
@Inject
constructor(
    private val repository: TranslationRepository,
    private val stringProvider: StringProvider,
    private val welcomeMessageProvider: WelcomeMessageProvider
) : ViewModel() {
  private val _state = MutableStateFlow<TranslateViewState>(TranslateViewState.Empty)
  val pageState: StateFlow<TranslateViewState> = _state

  private val _welcomeMessage = MutableStateFlow(welcomeMessageProvider.getRandomMessage())
  val welcomeMessage: StateFlow<TranslationFlowMessages> = _welcomeMessage

  // Track previous state for back navigation
  private var previousSearchResults: TranslateViewState.OptionsFetched? = null

  fun searchArticles(sourceLang: String, term: String) {
    _state.value = TranslateViewState.FetchingOptions
    viewModelScope.launch {
      try {
        _state.value =
            TranslateViewState.OptionsFetched(
                term, repository.searchArticles(sourceLang, term), emptyMap())
      } catch (e: Exception) {
        Log.e("TranslateViewModel", "Error fetching search results", e)
        val errorType = e.javaClass.simpleName
        val errorMessage = e.message ?: stringProvider.getString(R.string.error_unknown)
        _state.value = TranslateViewState.Error(errorType, errorMessage)
      }
    }
  }

  fun fetchTranslation(
      sources: TranslateViewState.OptionsFetched,
      searchPage: OptionalSourceTerm,
      sourceLang: String,
      targetLang: String
  ) {
    val updatedSources =
        TranslateViewState.OptionsFetched(
            sources.searchTerm,
            sources.options,
            sources.translations.plus(Pair(searchPage, TranslationState.Translating)))
    _state.value = updatedSources
    viewModelScope.launch {
      try {
        val translations = repository.fetchTranslations(searchPage, sourceLang, targetLang)
        val langTranslation = translations.find { it.targetLangCode == targetLang }
        val translationState =
            if (langTranslation == null) {
              // Target language not available, translations contains all available translations
              TranslationState.MissingTranslation(targetLang, translations)
            } else {
              TranslationState.Translated(langTranslation)
            }
        val finalSources =
            TranslateViewState.OptionsFetched(
                updatedSources.searchTerm,
                updatedSources.options,
                updatedSources.translations.plus(Pair(searchPage, translationState)))
        // Save search results before transitioning to Translated state for back navigation
        previousSearchResults = finalSources
        _state.value =
            TranslateViewState.Translated(searchPage, sourceLang, targetLang, translationState)
      } catch (e: Exception) {
        Log.e("TranslateViewModel", "Error fetching translation", e)
        val errorMessage = e.message ?: stringProvider.getString(R.string.error_unknown)
        _state.value =
            TranslateViewState.OptionsFetched(
                sources.searchTerm,
                sources.options,
                sources.translations.plus(Pair(searchPage, TranslationState.Error(errorMessage))))
      }
    }
  }

  /**
   * Navigate back from Translated state to the previous search results. If no previous results
   * exist, clears to Empty state.
   */
  fun backToSearchResults() {
    previousSearchResults?.let { _state.value = it }
        ?: run { clearSearch() }
  }

  /** Clear search and return to Empty state. Also clears saved search results. */
  fun clearSearch() {
    _state.value = TranslateViewState.Empty
    previousSearchResults = null
    _welcomeMessage.value = welcomeMessageProvider.getRandomMessage()
  }
}
