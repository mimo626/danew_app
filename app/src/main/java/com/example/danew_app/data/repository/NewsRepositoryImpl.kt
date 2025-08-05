package com.example.danew_app.data.repository

import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.data.remote.NewsApi
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {
    override suspend fun getNewsByCategory(category: String): List<NewsModel> {
        return api.fetchNewsByCategory(category = category).results.map { it.toDomain() }
    }
}
