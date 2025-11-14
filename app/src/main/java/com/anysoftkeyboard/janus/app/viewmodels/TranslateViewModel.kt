package com.anysoftkeyboard.janus.app.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anysoftkeyboard.janus.app.repository.OptionalSourceTerm
import com.anysoftkeyboard.janus.app.repository.TranslationRepository
import com.anysoftkeyboard.janus.database.entities.Translation
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

  object Error : TranslationState()
}

sealed class TranslateViewState() {
  object Empty : TranslateViewState()

  object FetchingOptions : TranslateViewState()

  data class OptionsFetched(
      val options: List<OptionalSourceTerm>,
      val translations: Map<OptionalSourceTerm, TranslationState>
  ) : TranslateViewState()

  data class Translated(
      val term: OptionalSourceTerm,
      val sourceLang: String,
      val targetLang: String,
      val translation: TranslationState
  ) : TranslateViewState()

  object Error : TranslateViewState()
}

@HiltViewModel
class TranslateViewModel @Inject constructor(private val repository: TranslationRepository) :
    ViewModel() {
  private val _state = MutableStateFlow<TranslateViewState>(TranslateViewState.Empty)
  val pageState: StateFlow<TranslateViewState> = _state

  fun searchArticles(sourceLang: String, term: String) {
    _state.value = TranslateViewState.FetchingOptions
    viewModelScope.launch {
      try {
        _state.value =
            TranslateViewState.OptionsFetched(
                repository.searchArticles(sourceLang, term), emptyMap())
      } catch (e: Exception) {
        Log.e("TranslateViewModel", "Error fetching search results", e)
        // Toast.makeText(context.applicationContext, "Error: ${e.message}",
        // Toast.LENGTH_SHORT).show()
        _state.value = TranslateViewState.Error
      }
    }
  }

  fun fetchTranslation(
      sources: TranslateViewState.OptionsFetched,
      searchPage: OptionalSourceTerm,
      sourceLang: String,
      targetLang: String
  ) {
    _state.value =
        TranslateViewState.OptionsFetched(
            sources.options,
            sources.translations.plus(Pair(searchPage, TranslationState.Translating)))
    viewModelScope.launch {
      try {
        val translations = repository.fetchTranslations(searchPage, sourceLang)
        val langTranslation = translations.find { it.targetLangCode == targetLang }
        val translationState =
            if (langTranslation == null) {
              TranslationState.MissingTranslation(targetLang, translations)
            } else {
              TranslationState.Translated(langTranslation)
            }
        sources.translations.plus(Pair(searchPage, translationState))
        _state.value =
            TranslateViewState.Translated(searchPage, sourceLang, targetLang, translationState)
      } catch (e: Exception) {
        Log.e("TranslateViewModel", "Error fetching translation", e)
        // Toast.makeText(context.applicationContext, "Error: ${e.message}",
        // Toast.LENGTH_SHORT).show()
        _state.value =
            TranslateViewState.OptionsFetched(
                sources.options,
                sources.translations.plus(Pair(searchPage, TranslationState.Error)))
      }
    }
  }
}
