package com.example.danew_app.data.entity

import kotlinx.serialization.SerialName

data class NewsEntity(
    @SerialName("article_id") val article_id: String? = null,
    val title: String? = null,
    val link: String? = null,
    val keywords: List<String> = emptyList(),
    val creator: List<String>? = emptyList(),
    val description: String? = null,
    val content: String? = null,
    @SerialName("pub_date") val pubDate: String,  // ISO8601 형식 문자열
    @SerialName("pub_date_tz") val pubDateTz: String? = null,
    @SerialName("image_url") val image_url: String? = null,
    @SerialName("video_url") val video_url: String? = null,
    @SerialName("source_id") val source_id: String? = null,
    @SerialName("source_name") val source_name: String? = null,
    @SerialName("source_priority") val source_priority: Int,
    @SerialName("source_url") val source_url: String? = null,
    @SerialName("source_icon") val source_icon: String? = null,
    val language: String? = null,
    val country: List<String> = emptyList(),
    val category: List<String>? = emptyList(),
    val sentiment: String? = null,
    @SerialName("sentiment_stats") val sentiment_stats: String? = null,
    @SerialName("ai_tag") val ai_tag: String? = null,
    @SerialName("ai_region") val ai_region: String? = null,
    @SerialName("ai_org") val ai_org: String? = null,
    val duplicate: Boolean
)
