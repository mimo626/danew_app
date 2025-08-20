package com.example.danew.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: String,   // 유저의 고유 ID (PK)
    val password: String,
    val name: String,
    val age: Int,
    val gender: String,
    val createdAt: String,
    val keywordList: List<String> = emptyList(),
    val customList: List<String>? = emptyList()
)
