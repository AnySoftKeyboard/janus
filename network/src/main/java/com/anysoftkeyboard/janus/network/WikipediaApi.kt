package com.anysoftkeyboard.janus.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WikipediaApi {
  @GET("api.php?action=query&list=search&format=json")
  suspend fun search(
      @Query("srsearch") searchTerm: String,
      @Query("srlimit") limit: Int = 10,
      @Query("srprop") properties: String = "snippet"
  ): SearchResponse

  @GET("api.php?action=query&prop=langlinks&format=json&lllimit=max")
  suspend fun getLangLinksForTitles(@Query("titles") titles: String): LangLinksResponse

  @GET("api.php?action=query&prop=langlinks|pageprops&format=json&lllimit=max")
  suspend fun getAllInfo(@Query("pageids") pageIds: String): LangLinksResponse

  @GET("api.php?action=query&generator=links&gpllimit=max&prop=pageprops&format=json")
  suspend fun getLinks(@Query("pageids") pageIds: String): LangLinksResponse
}
