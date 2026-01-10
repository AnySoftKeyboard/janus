package com.anysoftkeyboard.janus.app

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anysoftkeyboard.janus.app.di.AppModule
import com.anysoftkeyboard.janus.app.repository.RecentLanguagesRepository
import com.anysoftkeyboard.janus.app.repository.TranslationRepository
import com.anysoftkeyboard.janus.app.util.TranslationFlowMessages
import com.anysoftkeyboard.janus.app.util.TranslationFlowMessagesProvider
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLooper

@HiltAndroidTest
@UninstallModules(AppModule::class)
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

  @get:Rule val hiltRule = HiltAndroidRule(this)

  @BindValue val translationRepository: TranslationRepository = mock()

  @BindValue val recentLanguagesRepository: RecentLanguagesRepository = mock()

  @BindValue val translationFlowMessagesProvider: TranslationFlowMessagesProvider = mock()

  @BindValue val languageDetector: com.anysoftkeyboard.janus.app.util.LanguageDetector = mock()

  @Before
  fun setup() {
    // Mock default behaviors
    whenever(recentLanguagesRepository.recentLanguages).thenReturn(MutableStateFlow(emptyList()))
    whenever(recentLanguagesRepository.currentSourceLanguage).thenReturn(MutableStateFlow("en"))
    whenever(recentLanguagesRepository.currentTargetLanguage).thenReturn(MutableStateFlow("es"))
    whenever(translationRepository.getHistory()).thenReturn(MutableStateFlow(emptyList()))
    whenever(translationRepository.getBookmarks()).thenReturn(MutableStateFlow(emptyList()))

    whenever(translationFlowMessagesProvider.getRandomMessage())
        .thenReturn(
            TranslationFlowMessages(
                R.string.app_name,
                R.string.app_name,
                R.string.app_name,
                R.string.app_name,
            )
        )
  }

  @Test
  fun startMainActivity_withoutProcessText_doesNotSearch() = runTest {
    val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
    ActivityScenario.launch<MainActivity>(intent).use {
      ShadowLooper.idleMainLooper()
      // Verify NO search happened.
      // TranslationRepository.searchArticles is a suspend function, checking blocking
      // interactions might be tricky directly if we don't control the dispatcher.
      // But simpler: verify zero interactions with searchArticles
      verify(translationRepository, org.mockito.kotlin.never()).searchArticles(any(), any())
    }
  }

  @Test
  fun startMainActivity_withProcessText_triggersSearch() = runTest {
    val textToProcess = "Hello World"
    val intent =
        Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java).apply {
          action = Intent.ACTION_PROCESS_TEXT
          putExtra(Intent.EXTRA_PROCESS_TEXT, textToProcess)
          type = "text/plain"
        }

    // Mock search response to avoid crashes if called
    whenever(translationRepository.searchArticles(any(), any())).thenReturn(emptyList())

    ActivityScenario.launch<MainActivity>(intent).use {
      ShadowLooper.idleMainLooper()
      // Verify search WAS called with the text
      verify(translationRepository).searchArticles(any(), eq(textToProcess))
    }
  }
}
