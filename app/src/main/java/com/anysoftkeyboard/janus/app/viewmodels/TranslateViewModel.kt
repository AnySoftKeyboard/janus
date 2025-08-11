package com.anysoftkeyboard.janus.app.viewmodels

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

@HiltViewModel
class TranslateViewModel @Inject constructor(private val repository: TranslationRepository) :
    ViewModel() {
  private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
  val searchResults: StateFlow<List<SearchResult>> = _searchResults

  private val _translation = MutableStateFlow<Translation?>(null)
  val translation: StateFlow<Translation?> = _translation

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading

  private val _errorFetchingTranslation = MutableStateFlow(false)
  val errorFetchingTranslation: StateFlow<Boolean> = _errorFetchingTranslation

  fun searchArticles(lang: String, term: String) {
    viewModelScope.launch {
      _isLoading.value = true
      _errorFetchingTranslation.value = false
      _searchResults.value = repository.searchArticles(lang, term)
      _translation.value = null
      _isLoading.value = false
    }
  }

  fun fetchTranslation(pageId: Long, sourceLang: String, targetLang: String) {
    viewModelScope.launch {
      _isLoading.value = true
      _errorFetchingTranslation.value = false
      try {
        _translation.value = repository.fetchTranslation(pageId, sourceLang, targetLang)
      } catch (e: Exception) {
        _errorFetchingTranslation.value = true
        _translation.value = null // Clear previous translation on error
      } finally {
        _isLoading.value = false
      }
    }
  }
}
