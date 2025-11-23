package com.example.danew_app.data.remote

import com.example.danew_app.data.dto.AnnounceRequest
import com.example.danew_app.data.dto.ApiResponse
import com.example.danew_app.data.entity.AnnounceEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AnnounceApi {
    @POST("api/announce/save")
    fun saveAnnounce(@Body announceRequest: AnnounceRequest): Call<ApiResponse<AnnounceEntity>>

    @GET("api/announce/list")
    fun getAnnounceList(): Call<ApiResponse<List<AnnounceEntity>>>

    @DELETE("api/announce/delete/{announceId}")
    fun deleteAnnounce(@Path("announceId") announceId: String): Call<ApiResponse<String>>
}