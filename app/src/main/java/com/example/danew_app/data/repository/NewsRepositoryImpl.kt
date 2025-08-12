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
    private var nextPage: String? = null

    override suspend fun getNewsByCategory(category: String, loadMore:Boolean): List<NewsModel> {
        val result = api.fetchNewsByCategory(
            category = category,
            page = if (loadMore) nextPage else null
        )

        nextPage = result.nextPage // 다음 호출 때 사용할 page 값 저장
        Log.d("NewsResponse", "getNewsByCategory: ${result}")

        return result.results.map { it.toDomain() }
    }

    override suspend fun getNewsById(id: String): List<NewsModel> {
        val result = api.fetchNewsById(id = id).results
        return result.map { it.toDomain() }
    }

    override suspend fun getNewsBySearchQuery(searchQuery: String, loadMore:Boolean): List<NewsModel> {
        val result = api.fetchNewsBySearchQuery(
            searchQuery = searchQuery,
            page = if (loadMore) nextPage else null
        )

        nextPage = result.nextPage // 다음 호출 때 사용할 page 값 저장
        Log.d("NewsResponse", "getNewsBySearchQuery: ${result}")

        return result.results.map { it.toDomain() }
    }
}
