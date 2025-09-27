package com.example.danew_app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.data.remote.NewsApi
import com.example.danew_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getNewsByCategory(category: String): Flow<PagingData<NewsEntity>> {
        return Pager(PagingConfig(pageSize = 20)) {
            CategoryNewsPagingSource(api, category = category)
        }.flow
    }

    override suspend fun getNewsBySearchQuery(searchQuery: String): Flow<PagingData<NewsEntity>> {

        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                // 검색어가 바뀔 때마다 새로운 PagingSource 인스턴스가 생성됨
                SearchNewsPagingSource(api, searchQuery)
            }
        ).flow
    }

    override suspend fun getNewsById(id: String): List<NewsEntity> {
        val result = api.fetchNewsById(id = id).results
        return result.map { it}
    }

}
