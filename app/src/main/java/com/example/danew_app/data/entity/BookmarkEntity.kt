package com.example.danew_app.data.entity

import androidx.room.Entity

@Entity(
    tableName = "bookmark",
    primaryKeys = ["userId", "articleId"]  // 복합키 설정
)
data class BookmarkEntity(
    val userId: String,
    val articleId: String,
    val bookmarkedAt: String
)
