package com.anysoftkeyboard.janus.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anysoftkeyboard.janus.app.repository.TranslationRepository
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import com.anysoftkeyboard.janus.database.entities.Translation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: TranslationRepository) :
    ViewModel() {

  // Search query state
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  // History that automatically updates based on search query
  val history: StateFlow<List<UiTranslation>> =
      _searchQuery
          // Debounce search to reduce DB queries, but update immediately on clear
          .debounce { if (it.isBlank()) 0L else 300L }
          .flatMapLatest { query ->
            if (query.isBlank()) {
              repository.getHistory()
            } else {
              repository.searchHistory(query)
            }
          }
          .map { list -> list.map { UiTranslation.fromTranslation(it) } }
          .flowOn(Dispatchers.Default)
          .stateIn(
              scope = viewModelScope,
              started = SharingStarted.WhileSubscribed(5000),
              initialValue = emptyList(),
          )

  fun updateSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun clearSearch() {
    _searchQuery.value = ""
  }

  fun deleteTranslation(id: Int) {
    viewModelScope.launch { repository.deleteTranslation(id) }
  }

  fun restoreTranslation(translation: UiTranslation) {
    viewModelScope.launch { repository.restoreTranslation(translation.toTranslation()) }
  }
}
