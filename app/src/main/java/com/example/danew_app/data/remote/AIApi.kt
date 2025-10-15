package com.example.danew_app.data.remote;

import com.example.danew_app.data.dto.ApiResponse;

import retrofit2.http.Body
import retrofit2.http.POST

interface AIApi {
    @POST("api/news/summarize")
    suspend fun getSummarizeNews(
        @Body request: Map<String, String>
    ): ApiResponse<String>
}