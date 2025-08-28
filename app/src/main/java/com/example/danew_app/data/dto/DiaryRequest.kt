package com.example.danew_app.data.dto

import com.google.gson.annotations.SerializedName

data class DiaryRequest (
    @SerializedName("content")
    val content : String,
)