package com.example.danew_app.domain.usecase

import android.util.Log
import com.example.danew_app.data.entity.MetaNewsEntity
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.data.mapper.toMeta
import com.example.danew_app.domain.model.BookmarkModel
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.BookmarkRepository
import javax.inject.Inject

class SaveBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
){
    suspend operator fun invoke(token:String, newsModel:NewsModel): BookmarkModel{
        val metaNewsEntity = newsModel.toMeta()
        return bookmarkRepository.saveBookmark(token, metaNewsEntity).toDomain()
    }
}