package com.example.danew_app.data.remote

import com.example.danew_app.data.entity.BookmarkEntity
import com.example.danew_app.data.entity.MetaNewsEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BookmarkApi {
    @POST("api/bookmark/save")
    fun saveBookmark(@Header("Authorization") token: String,
                     @Body news: MetaNewsEntity): Call<BookmarkEntity>

    @GET("api/bookmark/getBookmarks")
    fun getBookmarks(@Header("Authorization") token: String):Call<List<MetaNewsEntity>>
}