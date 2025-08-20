package com.example.danew_app.domain.usecase

import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsByIdUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(id: String): List<NewsModel> {
        return repository.getNewsById(id).map { it.toDomain() }
    }
}
