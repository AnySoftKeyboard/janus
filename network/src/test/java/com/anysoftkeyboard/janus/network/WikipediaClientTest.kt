package com.anysoftkeyboard.janus.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WikipediaClientTest {

  private lateinit var mockWebServer: MockWebServer
  private lateinit var wikipediaApi: WikipediaApi

  @Before
  fun setup() {
    mockWebServer = MockWebServer()
    mockWebServer.start()

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    wikipediaApi =
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(WikipediaApi::class.java)
  }

  @After
  fun teardown() {
    mockWebServer.shutdown()
  }

  @Test
  fun `test search response parsing`() = runBlocking {
    val jsonResponse =
        """
            {
              "batchcomplete": "",
              "continue": {
                "sroffset": 10,
                "continue": "-|||"
              },
              "query": {
                "searchinfo": {
                  "totalhits": 901620,
                  "suggestion": "somer",
                  "suggestionsnippet": "somer"
                },
                "search": [
                  {
                    "ns": 0,
                    "title": "Summer",
                    "pageid": 29392,
                    "size": 22710,
                    "wordcount": 2461,
                    "snippet": "<span class=\"searchmatch\">Summer</span> or summertime is the hottest and brightest of the four temperate seasons, occurring after spring and before autumn. At or centred on the summer",
                    "timestamp": "2025-06-27T12:32:40Z"
                  },
                  {
                    "ns": 0,
                    "title": "Monster Summer",
                    "pageid": 71763900,
                    "size": 10071,
                    "wordcount": 773,
                    "snippet": "Monster <span class=\"searchmatch\">Summer</span> is a 2024 American adventure horror film directed by David Henrie, written by Cornelius Uliano and Bryan Schulz, and starring Mason Thames",
                    "timestamp": "2025-08-09T19:56:14Z"
                  }
                ]
              }
            }
        """
            .trimIndent()

    mockWebServer.enqueue(MockResponse().setBody(jsonResponse))

    val response = wikipediaApi.search("summer")

    assertEquals("", response.batchcomplete)
    assertEquals(10, response.continueData?.sroffset)
    assertEquals("-|||", response.continueData?.continueVal)
    assertEquals(901620, response.query.searchinfo.totalhits)
    assertEquals("somer", response.query.searchinfo.suggestion)
    assertEquals("somer", response.query.searchinfo.suggestionsnippet)
    assertEquals(2, response.query.search.size)
    assertEquals("Summer", response.query.search[0].title)
    assertEquals(29392, response.query.search[0].pageid)
    assertEquals(
        "<span class=\"searchmatch\">Summer</span> or summertime is the hottest and brightest of the four temperate seasons, occurring after spring and before autumn. At or centred on the summer",
        response.query.search[0].snippet)
  }
}
