package com.anysoftkeyboard.janus.app.di

import android.content.Context
import androidx.room.Room
import com.anysoftkeyboard.janus.app.repository.TranslationRepository
import com.anysoftkeyboard.janus.app.util.AndroidStringProvider
import com.anysoftkeyboard.janus.app.util.StringProvider
import com.anysoftkeyboard.janus.database.AppDatabase
import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.network.WikipediaApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
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
  private const val URL_LANG_PLACEHOLDER = "[LANG]"
  private const val BASE_URL = "https://${URL_LANG_PLACEHOLDER}.wikipedia.org/w/"
  private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB

  @Provides
  @Singleton
  fun provideTranslationRepository(
      translationDao: TranslationDao,
      wikipediaApi: LangWikipediaFactory,
      stringProvider: StringProvider,
  ): TranslationRepository {
    return TranslationRepository(translationDao, wikipediaApi, stringProvider)
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
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
        .addInterceptor { chain ->
          val request =
              chain
                  .request()
                  .newBuilder()
                  .header(
                      "User-Agent",
                      "Janus-translations-android/1.0 (ask@evendanan.net) OkHttp/5.x",
                  )
                  .build()
          chain.proceed(request)
        }
        .cache(cache)
        .build()
  }

  @Provides
  @Singleton
  fun provideRetrofitFactor(okHttpClient: OkHttpClient, moshi: Moshi): LangRetrofitFactory {
    return object : LangRetrofitFactory {
      override fun createRetrofit(sourceLang: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL.replace(URL_LANG_PLACEHOLDER, sourceLang))
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
      }
    }
  }

  @Provides
  @Singleton
  fun provideWikipediaApi(retrofit: LangRetrofitFactory): LangWikipediaFactory {
    return object : LangWikipediaFactory {
      override fun createWikipediaApi(sourceLang: String): WikipediaApi {
        return retrofit.createRetrofit(sourceLang).create(WikipediaApi::class.java)
      }
    }
  }

  @Provides
  @Singleton
  fun provideSharedPreferences(
      @ApplicationContext context: Context
  ): android.content.SharedPreferences {
    return context.getSharedPreferences("janus_prefs", Context.MODE_PRIVATE)
  }

  @Provides
  @Singleton
  fun provideLanguageDetector(
      real: com.anysoftkeyboard.janus.app.util.GeminiLanguageDetector
  ): com.anysoftkeyboard.janus.app.util.LanguageDetector {
    return real
  }
}

interface LangRetrofitFactory {
  fun createRetrofit(sourceLang: String): Retrofit
}

interface LangWikipediaFactory {
  fun createWikipediaApi(sourceLang: String): WikipediaApi
}

@Module
@InstallIn(SingletonComponent::class)
abstract class StringProviderModule {
  @Binds @Singleton abstract fun bindStringProvider(impl: AndroidStringProvider): StringProvider
}
