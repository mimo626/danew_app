package com.example.danew_app.data.dto

import com.google.gson.annotations.SerializedName

data class AnnounceRequest (
    @SerializedName("title")
    val title : String,
    @SerializedName("content")
    val content : String,
)