package com.anysoftkeyboard.janus.app.repository

import android.content.SharedPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RecentLanguagesRepositoryTest {
  private lateinit var sharedPreferences: SharedPreferences
  private lateinit var editor: SharedPreferences.Editor
  private lateinit var repository: RecentLanguagesRepository

  @Before
  fun setup() {
    sharedPreferences = mock()
    editor = mock()
    whenever(sharedPreferences.edit()).thenReturn(editor)
    whenever(editor.putString(any(), any())).thenReturn(editor)
  }

  @Test
  fun `initial load from empty prefs`() = runTest {
    whenever(sharedPreferences.getString(any(), any())).thenReturn("")
    repository = RecentLanguagesRepository(sharedPreferences)

    val list = repository.recentLanguages.first()
    assertEquals(emptyList<String>(), list)
  }

  @Test
  fun `initial load from existing prefs`() = runTest {
    whenever(sharedPreferences.getString(any(), any())).thenReturn("fr,de")
    repository = RecentLanguagesRepository(sharedPreferences)

    val list = repository.recentLanguages.first()
    assertEquals(listOf("fr", "de"), list)
  }

  @Test
  fun `addRecentLanguage adds to top`() = runTest {
    whenever(sharedPreferences.getString(any(), any())).thenReturn("fr")
    repository = RecentLanguagesRepository(sharedPreferences)

    repository.addRecentLanguage("es")

    val list = repository.recentLanguages.first()
    assertEquals(listOf("es", "fr"), list)
    verify(editor).putString(eq("recent_languages_list"), eq("es,fr"))
  }

  @Test
  fun `addRecentLanguage moves existing to top`() = runTest {
    whenever(sharedPreferences.getString(any(), any())).thenReturn("fr,es")
    repository = RecentLanguagesRepository(sharedPreferences)

    repository.addRecentLanguage("es")

    val list = repository.recentLanguages.first()
    assertEquals(listOf("es", "fr"), list)
    verify(editor).putString(eq("recent_languages_list"), eq("es,fr"))
  }

  @Test
  fun `addRecentLanguage caps at 4`() = runTest {
    whenever(sharedPreferences.getString(any(), any())).thenReturn("1,2,3,4")
    repository = RecentLanguagesRepository(sharedPreferences)

    repository.addRecentLanguage("5")

    val list = repository.recentLanguages.first()
    assertEquals(listOf("5", "1", "2", "3"), list)
    verify(editor).putString(eq("recent_languages_list"), eq("5,1,2,3"))
  }
}
