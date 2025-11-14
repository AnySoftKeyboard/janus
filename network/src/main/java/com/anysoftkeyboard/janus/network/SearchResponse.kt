package com.anysoftkeyboard.janus.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(@Json(name = "query") val query: Query?)

@JsonClass(generateAdapter = true)
data class Query(
    @Json(name = "searchinfo") val searchinfo: SearchInfo?,
    @Json(name = "search") val search: List<SearchResult>?
)

@JsonClass(generateAdapter = true)
data class SearchInfo(
    @Json(name = "totalhits") val totalhits: Int,
    @Json(name = "suggestion") val suggestion: String?,
    @Json(name = "suggestionsnippet") val suggestionsnippet: String?
)

@JsonClass(generateAdapter = true)
data class SearchResult(
    @Json(name = "title") val title: String,
    @Json(name = "pageid") val pageid: Long,
    @Json(name = "snippet") val snippet: String,
)

@JsonClass(generateAdapter = true)
data class LangLinksResponse(@Json(name = "query") val query: LangLinksQuery)

@JsonClass(generateAdapter = true)
data class LangLinksQuery(@Json(name = "pages") val pages: Map<String, PageLangLinks>)

@JsonClass(generateAdapter = true)
data class PageLangLinks(
    @Json(name = "pageid") val pageid: Long,
    @Json(name = "ns") val ns: Int,
    @Json(name = "title") val title: String,
    @Json(name = "langlinks") val langLinks: List<LangLink>?,
    @Json(name = "pageprops") val pageProps: PageProps?,
    @Json(name = "links") val links: List<Link>?
)

@JsonClass(generateAdapter = true) data class Link(@Json(name = "title") val title: String)

@JsonClass(generateAdapter = true)
data class LangLink(@Json(name = "lang") val lang: String, @Json(name = "*") val title: String)

@JsonClass(generateAdapter = true)
data class PageProps(
    @Json(name = "disambiguation") val disambiguation: String?,
    @Json(name = "wikibase-shortdesc") val wikibaseShortdesc: String?
)
