package com.example.danew_app.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarkNewsUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
){
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(newsId:String):NewsModel{
        val metaNews = bookmarkRepository.getBookmarkNews(newsId)
        val newsModel  = metaNews.toDomain()
        return newsModel
    }
}