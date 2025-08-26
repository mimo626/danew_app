package com.example.danew_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = false)
    val diaryId: String,
    val userId: String,
    val createdAt: String,
    val content: String?,
)
