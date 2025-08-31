package com.example.danew_app.domain.repository

import com.example.danew_app.data.entity.BookmarkEntity
import com.example.danew_app.data.entity.MetaNewsEntity

interface BookmarkRepository {
    suspend fun saveBookmark(token:String, metaNewsEntity: MetaNewsEntity):BookmarkEntity
    suspend fun getBookmarks(token: String):List<MetaNewsEntity>
    suspend fun deleteBookmark(token:String, newsId:String):Boolean
}