package com.anysoftkeyboard.janus.app.repository

import android.content.SharedPreferences
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecentLanguagesRepository
@Inject
constructor(
    private val sharedPreferences: SharedPreferences,
) {
  private val _recentLanguages = MutableStateFlow<List<String>>(emptyList())
  val recentLanguages: StateFlow<List<String>> = _recentLanguages.asStateFlow()

  // Current Source Language
  private val _currentSourceLanguage = MutableStateFlow("en")
  val currentSourceLanguage: StateFlow<String> = _currentSourceLanguage.asStateFlow()

  // Current Target Language
  private val _currentTargetLanguage = MutableStateFlow("he")
  val currentTargetLanguage: StateFlow<String> = _currentTargetLanguage.asStateFlow()

  init {
    loadRecentLanguages()
    loadCurrentLanguages()
  }

  private fun loadRecentLanguages() {
    val saved = sharedPreferences.getString(KEY_RECENT_LANGUAGES, "") ?: ""
    if (saved.isNotEmpty()) {
      _recentLanguages.value = saved.split(",").filter { it.isNotEmpty() }
    }
  }

  fun addRecentLanguage(code: String) {
    if (code.isEmpty()) return

    val currentList = _recentLanguages.value.toMutableList()
    // Remove if exists to move to top
    currentList.remove(code)
    // Add to top
    currentList.add(0, code)
    // Cap at 4
    if (currentList.size > 4) {
      currentList.removeAt(currentList.lastIndex)
    }

    _recentLanguages.value = currentList
    persist(currentList)
  }

  private fun persist(list: List<String>) {
    sharedPreferences.edit().putString(KEY_RECENT_LANGUAGES, list.joinToString(",")).apply()
  }

  fun setSourceLanguage(code: String) {
    if (code.isNotEmpty() && code != _currentSourceLanguage.value) {
      _currentSourceLanguage.value = code
      sharedPreferences.edit().putString(KEY_CURRENT_SOURCE_LANGUAGE, code).apply()
    }
  }

  fun setTargetLanguage(code: String) {
    if (code.isNotEmpty() && code != _currentTargetLanguage.value) {
      _currentTargetLanguage.value = code
      sharedPreferences.edit().putString(KEY_CURRENT_TARGET_LANGUAGE, code).apply()
    }
  }

  private fun loadCurrentLanguages() {
    val source = sharedPreferences.getString(KEY_CURRENT_SOURCE_LANGUAGE, "en") ?: "en"
    _currentSourceLanguage.value = source
    val target = sharedPreferences.getString(KEY_CURRENT_TARGET_LANGUAGE, "he") ?: "he"
    _currentTargetLanguage.value = target
  }

  companion object {
    private const val KEY_RECENT_LANGUAGES = "recent_languages_list"
    private const val KEY_CURRENT_SOURCE_LANGUAGE = "current_source_lang"
    private const val KEY_CURRENT_TARGET_LANGUAGE = "current_target_lang"
  }
}
