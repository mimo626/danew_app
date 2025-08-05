package com.example.danew_app.data.remote

import com.example.danew_app.domain.model.NewsModel
import com.google.gson.annotations.SerializedName

//API 응답을 받을 때 사용하는 DTO (Data Transfer Object).
data class NewsResponse(
    @SerializedName("results")
    val results: List<NewsModel>
)