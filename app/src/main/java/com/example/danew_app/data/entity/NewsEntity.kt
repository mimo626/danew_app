package com.example.danew_app.data.entity

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsEntity(
    @SerialName("article_id") val articleId: String? = null,
    val title: String? = null,
    val link: String? = null,
    val keywords: List<String> = emptyList(),
    val creator: List<String>? = emptyList(),
    val description: String? = null,
    val content: String? = null,
    @SerialName("pub_date") val pubDate: String,  // ISO8601 형식 문자열
    @SerialName("pub_date_tz") val pubDateTz: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("video_url") val videoUrl: String? = null,
    @SerialName("source_id") val sourceId: String? = null,
    @SerialName("source_name") val sourceName: String? = null,
    @SerialName("source_priority") val sourcePriority: Int,
    @SerialName("source_url") val sourceUrl: String? = null,
    @SerialName("source_icon") val sourceIcon: String? = null,
    val language: String? = null,
    val country: List<String> = emptyList(),
    val category: List<String>? = emptyList(),
    val sentiment: String? = null,
    @SerialName("sentiment_stats") val sentimentStats: String? = null,
    @SerialName("ai_tag") val aiTag: String? = null,
    @SerialName("ai_region") val aiRegion: String? = null,
    @SerialName("ai_org") val aiOrg: String? = null,
    val duplicate: Boolean
)
