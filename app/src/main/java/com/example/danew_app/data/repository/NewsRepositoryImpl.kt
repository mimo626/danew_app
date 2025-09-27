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

    override suspend fun getNews(category: String): Flow<PagingData<NewsEntity>> {
        return Pager(PagingConfig(pageSize = 20)) {
            NewsPagingSource(api, category)
        }.flow
    }

    override suspend fun getNewsByCategory(category: String, loadMore:Boolean): List<NewsEntity> {
        val result = api.fetchNewsByCategory(
            category = category,
            page = if (loadMore) nextPage else null
        )

        nextPage = result.nextPage // 다음 호출 때 사용할 page 값 저장
        Log.d("News 카테고리별 조회", "getNewsByCategory: ${result}")

        return result.results.map { it }
    }

    override suspend fun getNewsById(id: String): List<NewsEntity> {
        val result = api.fetchNewsById(id = id).results
        return result.map { it}
    }

    override suspend fun getNewsBySearchQuery(searchQuery: String, loadMore:Boolean): List<NewsEntity> {
        val result = api.fetchNewsBySearchQuery(
            searchQuery = searchQuery,
            //page = if (loadMore) nextPage else null
        )

        nextPage = result.nextPage // 다음 호출 때 사용할 page 값 저장
        Log.d("News 검색어 조회", "getNewsBySearchQuery: ${result}")

        return result.results.map { it }
    }
}
