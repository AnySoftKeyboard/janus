package com.anysoftkeyboard.janus.network

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WikipediaClient(context: Context) {
  private val BASE_URL = "https://en.wikipedia.org/w/"
  private val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB

  private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

  private val cache = Cache(File(context.cacheDir, "http-cache"), CACHE_SIZE)

  private val okHttpClient =
      OkHttpClient.Builder()
          .addInterceptor { chain ->
            val request =
                chain
                    .request()
                    .newBuilder()
                    .header(
                        "User-Agent",
                        "Janus-translations-android/1.0 (ask@evendanan.net) OkHttp/5.x")
                    .build()
            chain.proceed(request)
          }
          .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
          .cache(cache)
          .build()

  val api: WikipediaApi by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(WikipediaApi::class.java)
  }
}
