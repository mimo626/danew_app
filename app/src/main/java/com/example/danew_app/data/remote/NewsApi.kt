package com.example.danew_app.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

//Retrofit 인터페이스, API 요청의 명세를 정의
interface NewsApi {
    @GET("latest")
    suspend fun fetchNewsByCategory(
        @Query("country") country: String = "kr",
        @Query("category") category: String,
        @Query("apikey") apiKey: String = "pub_83780fb69a56440dbbeb6c4373fcb5ebb1535"
    ): NewsResponse

    @GET("latest")
    suspend fun fetchNewsById(
        @Query("id") id: String,
        @Query("apikey") apiKey: String = "pub_83780fb69a56440dbbeb6c4373fcb5ebb1535"
    ): NewsResponse

}
