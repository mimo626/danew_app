package com.example.danew_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "today_news", primaryKeys = ["userId", "newsId"])
data class TodayNewsEntity (
    val newsId: String,
    val userId: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val sourceName: String,
    val pubDate: String,
    val category: List<String>?,
    val creator: List<String>?,
    val keywords: List<String>?,
    val link: String?,
    val language: String?,
    val savedAt: Long = System.currentTimeMillis()
)