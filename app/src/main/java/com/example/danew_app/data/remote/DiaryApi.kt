package com.example.danew_app.data.remote

import com.example.danew_app.data.dto.DiaryRequest
import com.example.danew_app.data.entity.DiaryEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DiaryApi {
    @POST("api/diary/save")
    fun save(@Body diaryRequest: DiaryRequest,
             @Header("Authorization") token:String): Call<DiaryEntity>

    @GET("api/diary/getByDate")
    fun getByDate(@Header("Authorization") token:String,
                  @Query("createdAt") createdAt:String): Call<DiaryEntity?>

    @PUT("api/diary/update/{diaryId}")
    fun updateDiary(
        @Header("Authorization") token: String,
        @Path("diaryId") diaryId: String,
        @Body diaryRequest: DiaryRequest
    ): Call<DiaryEntity>
}