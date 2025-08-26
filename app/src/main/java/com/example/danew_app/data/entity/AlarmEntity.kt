package com.example.danew_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = false)
    val alarmId: String,
    val userId: String,
    val message: String,
    val createdAt: String,
    val isRead: Boolean,
)
