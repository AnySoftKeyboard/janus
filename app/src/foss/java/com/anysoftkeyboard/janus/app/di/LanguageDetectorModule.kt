package com.anysoftkeyboard.janus.app.di

import com.anysoftkeyboard.janus.app.util.LanguageDetector
import com.anysoftkeyboard.janus.app.util.OptimaizeLanguageDetector
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
  fun provideLanguageDetector(real: OptimaizeLanguageDetector): LanguageDetector {
    return real
  }
}
