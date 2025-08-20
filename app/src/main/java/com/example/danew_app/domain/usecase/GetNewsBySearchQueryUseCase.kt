package com.example.danew_app.domain.usecase

import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsBySearchQueryUseCase @Inject constructor(
    private val repository: NewsRepository
){
    suspend operator fun invoke(searchQuery: String, loadMore:Boolean=false):List<NewsModel>{
        return repository.getNewsBySearchQuery(searchQuery, loadMore).map { it.toDomain() }
    }
}