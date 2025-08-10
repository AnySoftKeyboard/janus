package com.anysoftkeyboard.janus.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anysoftkeyboard.janus.app.repository.TranslationRepository
import com.anysoftkeyboard.janus.database.entities.Translation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TranslateViewModel @Inject constructor(private val repository: TranslationRepository) :
    ViewModel() {
  private val _translations = MutableStateFlow<List<Translation>>(emptyList())
  val translations: StateFlow<List<Translation>> = _translations

  fun search(lang: String, term: String) {
    viewModelScope.launch { _translations.value = repository.search(lang, term) }
  }
}
