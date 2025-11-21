package com.example.danew_app.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.data.entity.NewsSummaryEntity
import com.example.danew_app.data.local.NewsSummaryDao
import com.example.danew_app.data.remote.AIApi
import com.example.danew_app.data.remote.NewsApi
import com.example.danew_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
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

        val newsFlow = Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchNewsPagingSource(newsApi, searchQuery)
            }
        ).flow

        // Flow 내부 아이템 로그 찍기
        return newsFlow.onEach { pagingData ->
            // PagingData 내부는 바로 접근 불가하므로 map으로 임시 변환
            val items = mutableListOf<NewsEntity>()
            pagingData.map { item ->
                items.add(item)
            }
            Log.i("뉴스 조회", "로딩된 뉴스 수: ${items.size}")
        }
    }

    override suspend fun getNewsById(id: String): Flow<NewsEntity> = flow {
        val result = newsApi.fetchNewsById(id = id).results
        val newsEntity = result[0]

        emit(newsEntity.copy(description = ""))

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
