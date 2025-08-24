package com.example.danew_app.data.remote//package com.example.danew.data.repository.retrofit;

import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.data.dto.LoginRequest

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @POST("api/auth/signup")
    fun save(@Body user: UserEntity): Call<UserEntity>

    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<String>

    @GET("api/auth/check-username")
    fun checkUserId(@Query("userId") userId: String): Call<Boolean>

}
