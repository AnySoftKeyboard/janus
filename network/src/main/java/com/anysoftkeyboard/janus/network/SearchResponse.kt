package com.anysoftkeyboard.janus.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "batchcomplete") val batchcomplete: String,
    @Json(name = "continue") val continueData: ContinueData?,
    @Json(name = "query") val query: Query
)

@JsonClass(generateAdapter = true)
data class ContinueData(
    @Json(name = "sroffset") val sroffset: Int,
    @Json(name = "continue") val continueVal: String
)

@JsonClass(generateAdapter = true)
data class Query(
    @Json(name = "searchinfo") val searchinfo: SearchInfo,
    @Json(name = "search") val search: List<SearchResult>
)

@JsonClass(generateAdapter = true)
data class SearchInfo(
    @Json(name = "totalhits") val totalhits: Int,
    @Json(name = "suggestion") val suggestion: String?,
    @Json(name = "suggestionsnippet") val suggestionsnippet: String?
)

@JsonClass(generateAdapter = true)
data class SearchResult(
    @Json(name = "ns") val ns: Int,
    @Json(name = "title") val title: String,
    @Json(name = "pageid") val pageid: Long,
    @Json(name = "size") val size: Int,
    @Json(name = "wordcount") val wordcount: Int,
    @Json(name = "snippet") val snippet: String,
    @Json(name = "timestamp") val timestamp: String
)
