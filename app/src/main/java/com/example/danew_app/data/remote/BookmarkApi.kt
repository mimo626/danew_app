package com.example.danew_app.data.remote

import com.example.danew_app.data.dto.ApiResponse
import com.example.danew_app.data.entity.BookmarkEntity
import com.example.danew_app.data.entity.MetaNewsEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BookmarkApi {
    @POST("api/bookmark/save")
    fun saveBookmark(@Header("Authorization") token: String,
                     @Body news: MetaNewsEntity): Call<ApiResponse<BookmarkEntity>>

    @GET("api/bookmark/getBookmarks")
    fun getBookmarks(
        @Header("Authorization") token: String):Call<ApiResponse<List<MetaNewsEntity>>>

    @GET("api/bookmark/getBookmarkNews/{articleId}")
    fun getBookmarkNews(
        @Path("articleId") articleId: String
    ): Call<ApiResponse<MetaNewsEntity>>

    @GET("api/bookmark/check-bookmark/{articleId}")
    fun checkBookmark(
        @Header("Authorization") token: String,
        @Path("articleId") articleId: String
    ): Call<ApiResponse<Boolean>>

    @DELETE("api/bookmark/delete/{id}")
    fun deleteBookmark(@Header("Authorization") token: String,
                       @Path("id") id: String): Call<ApiResponse<Boolean>>

}