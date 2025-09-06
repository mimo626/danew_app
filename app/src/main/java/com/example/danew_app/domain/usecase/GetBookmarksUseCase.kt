package com.example.danew_app.domain.usecase

import android.util.Log
import com.example.danew_app.data.entity.MetaNewsEntity
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
){
    suspend operator fun invoke(token:String):List<NewsModel>{
        val metaNewsList = bookmarkRepository.getBookmarks(token)
        val newsModelList = metaNewsList.map { metaNewsEntity -> metaNewsEntity.toDomain()}
        return newsModelList
    }
}