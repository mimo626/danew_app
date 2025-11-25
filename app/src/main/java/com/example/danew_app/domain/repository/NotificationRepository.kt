package com.example.danew_app.domain.repository

interface NotificationRepository {
    suspend fun sendNotification(token: String, title: String, content:String): String
}