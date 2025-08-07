package com.anysoftkeyboard.janus.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(@Json(name = "query") val query: Query)

@JsonClass(generateAdapter = true)
data class Query(@Json(name = "search") val search: List<SearchResult>)

@JsonClass(generateAdapter = true)
data class SearchResult(
    @Json(name = "title") val title: String,
    @Json(name = "snippet") val snippet: String
)
