package com.example.danew_app.data.dto

import com.example.danew_app.data.entity.NewsEntity
import com.google.gson.annotations.SerializedName

//API 응답을 받을 때 사용하는 DTO (Data Transfer Object).
data class NewsResponse(
    @SerializedName("totalResults")
    val totalResults: Int,

    @SerializedName("nextPage")
    val nextPage: String?,

    @SerializedName("results")
    val results: List<NewsEntity>,
)