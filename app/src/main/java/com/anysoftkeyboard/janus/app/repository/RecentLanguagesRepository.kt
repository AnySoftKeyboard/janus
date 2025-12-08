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

  init {
    loadRecentLanguages()
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

  companion object {
    private const val KEY_RECENT_LANGUAGES = "recent_languages_list"
  }
}
