package com.example.danew_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "today_news")
data class TodayNewsEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val newsId: String,
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
    val savedAt:String,
)