package com.example.danew_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "announce")
data class AnnounceEntity(
    @PrimaryKey(autoGenerate = false)
    val announceId: String,
    val title:String,
    val content: String,
    val createdAt: String,
)
