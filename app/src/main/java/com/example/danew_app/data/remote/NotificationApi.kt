package com.example.danew_app.data.remote

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface NotificationApi {
    @POST("/api/notifications/send")
    fun sendSelfNotification(
        @Header("X-FCM-Token") token: String,
        @Query("title") title: String,
        @Query("content") content: String
    ): Call<String>
}