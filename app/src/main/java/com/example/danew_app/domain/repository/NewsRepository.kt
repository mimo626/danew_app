package com.example.danew_app.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.danew_app.data.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsByCategory(category: String, loadMore:Boolean): List<NewsEntity>
    suspend fun getNews(category: String): Flow<PagingData<NewsEntity>>
    suspend fun getNewsById(id: String): List<NewsEntity>
    suspend fun getNewsBySearchQuery(searchQuery: String, loadMore:Boolean): List<NewsEntity>
}

