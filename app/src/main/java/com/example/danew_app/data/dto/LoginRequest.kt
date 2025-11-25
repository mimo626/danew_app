package com.example.danew_app.data.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("userId")
    val userId: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("fcmToken")
    val fcmToken: String,
)
