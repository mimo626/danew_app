package com.example.danew_app.data.remote//package com.example.danew.data.repository.retrofit;

import com.example.danew.data.local.entity.UserEntity

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface UserApi {
    @POST("api/auth/signup")
    fun save(@Body user: UserEntity): Call<UserEntity>
}
