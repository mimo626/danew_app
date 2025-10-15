package com.example.danew_app.data.entity

import androidx.room.*
import kotlinx.serialization.SerialName
import com.example.danew_app.data.converter.StringListConverter // 타입 컨버터 파일 경로

@Entity(tableName = "news")
@TypeConverters(StringListConverter::class) // 데이터베이스 클래스 레벨에서 지정하는 것도 가능
data class NewsEntity(
    @PrimaryKey
    @SerialName("article_id") val article_id: String, // Room의 Primary Key는 Nullable이면 안 됩니다.
    val title: String? = null,
    val link: String? = null,
    val keywords: List<String>? = emptyList(), // @TypeConverters 필요
    val creator: List<String>? = emptyList(), // @TypeConverters 필요
    val description: String? = null,
    val content: String? = null,
    @SerialName("pub_date") val pubDate: String,
    @SerialName("pub_date_tz") val pubDateTz: String? = null,
    @SerialName("image_url") val image_url: String? = null,
    @SerialName("video_url") val video_url: String? = null,
    @SerialName("source_id") val source_id: String? = null,
    @SerialName("source_name") val source_name: String? = null,
    @SerialName("source_priority") val source_priority: Int,
    @SerialName("source_url") val source_url: String? = null,
    @SerialName("source_icon") val source_icon: String? = null,
    val language: String? = null,
    val country: List<String> = emptyList(), // @TypeConverters 필요
    val category: List<String>? = emptyList(), // @TypeConverters 필요
    val sentiment: String? = null,
    @SerialName("sentiment_stats") val sentiment_stats: String? = null,
    @SerialName("ai_tag") val ai_tag: String? = null,
    @SerialName("ai_region") val ai_region: String? = null,
    @SerialName("ai_org") val ai_org: String? = null,
    val duplicate: Boolean
)