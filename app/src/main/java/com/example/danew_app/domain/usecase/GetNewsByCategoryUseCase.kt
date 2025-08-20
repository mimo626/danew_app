package com.example.danew_app.domain.usecase

import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsByCategoryUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(category: String, loadMore:Boolean=false): List<NewsModel> {
        return repository.getNewsByCategory(category, loadMore).map { it.toDomain() }
    }
}
