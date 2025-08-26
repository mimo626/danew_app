package com.example.danew_app.domain.model

data class AlarmModel (
    val alarmId: String,
    val userId: String,
    val message: String,
    val createdAt: String,
    val isRead: Boolean,
)