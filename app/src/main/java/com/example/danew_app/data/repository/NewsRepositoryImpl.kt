package com.example.danew_app.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.data.remote.NewsApi
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {
    private var nextPage: String? = null

    override suspend fun getNewsByCategory(category: String): Flow<PagingData<NewsEntity>> {
        return Pager(PagingConfig(pageSize = 20)) {
            NewsPagingSource(api, category)
        }.flow
    }

    override suspend fun getNewsBySearchQuery(searchQuery: String): Flow<PagingData<NewsEntity>> {
        return Pager(PagingConfig(pageSize = 20)) {
            NewsPagingSource(api, searchQuery = searchQuery)
        }.flow
    }

    override suspend fun getNewsById(id: String): List<NewsEntity> {
        val result = api.fetchNewsById(id = id).results
        return result.map { it}
    }

}
