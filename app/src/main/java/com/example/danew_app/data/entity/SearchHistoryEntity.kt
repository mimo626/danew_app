package com.example.danew_app.data.entity

import androidx.room.Entity

@Entity(tableName = "search_history", primaryKeys = ["userId", "keyword"])
data class SearchHistoryEntity(
    val userId: String,  // 유저를 구분할 식별자 추가 (토큰에서 추출한 ID 등)
    val keyword: String,
    val timestamp: Long = System.currentTimeMillis()
)