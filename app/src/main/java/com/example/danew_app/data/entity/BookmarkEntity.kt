package com.example.danew_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: String,
    val articleId: String,
    val bookmarkedAt: String,
)