package com.example.danew_app.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.data.entity.NewsSummaryEntity
import com.example.danew_app.data.local.NewsSummaryDao
import com.example.danew_app.data.remote.AIApi
import com.example.danew_app.data.remote.NewsApi
import com.example.danew_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val aiApi: AIApi,
    private val newsSummaryDao: NewsSummaryDao,
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

    override suspend fun getNewsById(id: String): Flow<NewsEntity> = flow {
        val result = newsApi.fetchNewsById(id = id).results
        val newsEntity = result[0]

        emit(newsEntity) // 원문 즉시 emit

        // 로컬 캐시 확인
        val cached = newsSummaryDao.getSummarizedNews(id)
        if (cached != null) {
            emit(newsEntity.copy(description = cached.summary))
        } else if (!newsEntity.description.isNullOrBlank()) {
            try {
                val newsMap = mapOf("content" to newsEntity.description, "articleId" to newsEntity.article_id)
                Log.i("News 요약", "${newsMap}",)

                val body = aiApi.getSummarizeNews(newsMap)
                if (body.status == "success" && body.data != null) {
                    val summarized = newsEntity.copy(description = body.data)
                    // DB에 저장
                    newsSummaryDao.insertNewsSummary(NewsSummaryEntity(id, body.data))
                    emit(summarized)
                }
            } catch (e: Exception) {
                Log.e("News 요약", "예외 발생", e)
            }
        }
    }


}
