package com.example.danew_app.data.remote

import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.data.dto.ApiResponse
import com.example.danew_app.data.dto.LoginRequest
import com.example.danew_app.data.dto.UserResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @POST("api/auth/signup")
    fun save(@Body user: UserEntity): Call<ApiResponse<UserResponse>>

    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<ApiResponse<UserResponse>>

    @GET("api/auth/check-username")
    fun checkUserId(@Query("userId") userId: String): Call<ApiResponse<Boolean>>

    @GET("/api/auth/getUser")
    fun getUser(@Header("Authorization") token: String) : Call<ApiResponse<UserEntity>>
}
