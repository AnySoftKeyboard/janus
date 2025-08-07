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
}
