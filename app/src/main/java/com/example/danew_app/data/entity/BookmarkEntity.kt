package com.example.danew_app.data.entity

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "bookmark", primaryKeys = ["userId", "articleId"])
data class BookmarkEntity(
    @Embedded val id: BookmarkId,
    val bookmarkedAt: String
)

data class BookmarkId(
    val userId: String,
    val articleId: String
)

