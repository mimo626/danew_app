package com.example.danew_app.data.remote

import com.example.danew_app.data.dto.ApiResponse
import com.example.danew_app.data.dto.QuestionRequest
import com.example.danew_app.data.entity.QuestionEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuestionApi {
    @POST("api/question/save")
    fun saveQuestion(@Body questionRequest: QuestionRequest): Call<ApiResponse<QuestionEntity>>

    @GET("api/question/list")
    fun getQuestionList(): Call<ApiResponse<List<QuestionEntity>>>

    @DELETE("api/question/delete/{questionId}")
    fun deleteQuestion(@Path("questionId") questionId: String): Call<ApiResponse<String>>
}