package com.anysoftkeyboard.janus.app.di

import android.content.Context
import androidx.room.Room
import com.anysoftkeyboard.janus.app.repository.TranslationRepository
import com.anysoftkeyboard.janus.database.AppDatabase
import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.network.WikipediaApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  private const val BASE_URL = "https://en.wikipedia.org/w/"
  private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB

  @Provides
  @Singleton
  fun provideTranslationRepository(
      translationDao: TranslationDao,
      wikipediaApi: WikipediaApi
  ): TranslationRepository {
    return TranslationRepository(translationDao, wikipediaApi)
  }

  @Provides
  @Singleton
  fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "janus_database").build()
  }

  @Provides
  @Singleton
  fun provideTranslationDao(appDatabase: AppDatabase): TranslationDao {
    return appDatabase.translationDao()
  }

  @Provides
  @Singleton
  fun provideMoshi(): Moshi {
    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
    val cache = Cache(File(context.cacheDir, "http-cache"), CACHE_SIZE)
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .cache(cache)
        .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
  }

  @Provides
  @Singleton
  fun provideWikipediaApi(retrofit: Retrofit): WikipediaApi {
    return retrofit.create(WikipediaApi::class.java)
  }
}
