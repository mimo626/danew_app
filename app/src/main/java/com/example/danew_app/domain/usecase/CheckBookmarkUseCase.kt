package com.example.danew_app.domain.usecase

import com.example.danew_app.domain.repository.BookmarkRepository
import javax.inject.Inject

class CheckBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
){
    suspend operator fun invoke(token: String, newsId:String) : Boolean{
        return bookmarkRepository.checkBookmark(token, newsId)
    }
}