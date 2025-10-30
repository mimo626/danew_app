package com.example.danew_app.data.remote

import com.example.danew_app.data.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

//Retrofit 인터페이스, API 요청의 명세를 정의
interface NewsApi {
    @GET("latest")
    suspend fun fetchNewsByCategory(
        @Query("language") language: String = "ko",
        @Query("country") country: String = "kr",
        @Query("category") category: String,
        @Query("apikey") apiKey: String = "pub_0e80e132174840a89888ba29e55ff405",
        @Query("page") page: String? = null
    ): NewsResponse

    @GET("latest")
    suspend fun fetchNewsById(
        @Query("id") id: String,
        @Query("apikey") apiKey: String = "pub_0e80e132174840a89888ba29e55ff405"
    ): NewsResponse

    @GET("latest")
    suspend fun fetchNewsBySearchQuery(
        @Query("q") searchQuery: String,
        @Query("language") language: String = "ko",
        @Query("country") country: String = "kr",
        @Query("apikey") apiKey: String = "pub_0e80e132174840a89888ba29e55ff405",
        @Query("page") page: String? = null
    ): NewsResponse

}
