package com.example.danew_app.data.repository

import android.util.Log
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
        val result = api.fetchNewsByCategory(category = category)
        Log.d("NewsResponse", "getNewsByCategory: ${result}")

        return result.results.map { it.toDomain() }
    }

    override suspend fun getNewsById(id: String): List<NewsModel> {
        val result = api.fetchNewsById(id = id).results
        return result.map { it.toDomain() }
    }
}
