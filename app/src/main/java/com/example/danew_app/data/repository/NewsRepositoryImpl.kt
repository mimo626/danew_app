package com.example.danew_app.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.danew_app.data.dto.ApiResponse
import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.data.remote.AIApi
import com.example.danew_app.data.remote.NewsApi
import com.example.danew_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resumeWithException

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val aiApi: AIApi
) : NewsRepository {

    override suspend fun getNewsByCategory(category: String): Flow<PagingData<NewsEntity>> {
        return Pager(PagingConfig(pageSize = 20)) {
            CategoryNewsPagingSource(newsApi, category = category)
        }.flow
    }

    override suspend fun getNewsBySearchQuery(searchQuery: String): Flow<PagingData<NewsEntity>> {

        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                // 검색어가 바뀔 때마다 새로운 PagingSource 인스턴스가 생성됨
                SearchNewsPagingSource(newsApi, searchQuery)
            }
        ).flow
    }

    override suspend fun getNewsById(id: String): NewsEntity {
        val result = newsApi.fetchNewsById(id = id).results
        val newsEntity = result[0]

        if (newsEntity.description != null) {
            try {
                val body = aiApi.getSummarizeNews(mapOf("prompt" to "${newsEntity.description}"))

                if (body.status == "success" && body.data != null) {
                    val summarized = newsEntity.copy(description = body.data)
                    Log.i("News 요약", "성공: $summarized")
                    return summarized
                } else {
                    val msg = body.message ?: "뉴스 요약 실패"
                    Log.e("News 요약", msg)
                    throw RuntimeException(msg)
                }
            } catch (e: Exception) {
                Log.e("News 요약", "예외 발생", e)
                throw e
            }
        }
        return newsEntity
    }
}
