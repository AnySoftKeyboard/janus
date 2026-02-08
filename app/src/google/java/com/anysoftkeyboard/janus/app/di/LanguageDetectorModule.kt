package com.anysoftkeyboard.janus.app.di

import com.anysoftkeyboard.janus.app.util.GeminiLanguageDetector
import com.anysoftkeyboard.janus.app.util.LanguageDetector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LanguageDetectorModule {
  @Provides
  @Singleton
  fun provideLanguageDetector(real: GeminiLanguageDetector): LanguageDetector {
    return real
  }
}
