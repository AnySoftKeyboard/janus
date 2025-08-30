package com.anysoftkeyboard.janus.app.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anysoftkeyboard.janus.app.repository.TranslationRepository
import com.anysoftkeyboard.janus.database.entities.Translation
import com.anysoftkeyboard.janus.network.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TranslationState() {
  object Translating : TranslationState()

  data class Translated(val translation: Translation) : TranslationState()

  object Error : TranslationState()
}

sealed class TranslateViewState() {
  object Empty : TranslateViewState()

  object FetchingOptions : TranslateViewState()

  data class OptionsFetched(
      val options: List<SearchResult>,
      val translations: Map<SearchResult, TranslationState>
  ) : TranslateViewState()

  object Error : TranslateViewState()
}

@HiltViewModel
class TranslateViewModel @Inject constructor(private val repository: TranslationRepository) :
    ViewModel() {
  private val _state = MutableStateFlow<TranslateViewState>(TranslateViewState.Empty)
  val pageState: StateFlow<TranslateViewState> = _state

  fun searchArticles(lang: String, term: String) {
    _state.value = TranslateViewState.FetchingOptions
    viewModelScope.launch {
      try {
        _state.value =
            TranslateViewState.OptionsFetched(repository.searchArticles(lang, term), emptyMap())
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
      searchPage: SearchResult,
      sourceLang: String,
      targetLang: String
  ) {
    _state.value =
        TranslateViewState.OptionsFetched(
            sources.options,
            sources.translations.plus(Pair(searchPage, TranslationState.Translating)))
    viewModelScope.launch {
      try {
        _state.value =
            TranslateViewState.OptionsFetched(
                sources.options,
                sources.translations.plus(
                    Pair(
                        searchPage,
                        TranslationState.Translated(
                            repository.fetchTranslation(searchPage, sourceLang, targetLang)))))
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
