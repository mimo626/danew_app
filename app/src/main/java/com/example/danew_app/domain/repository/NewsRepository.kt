package com.example.danew_app.domain.repository

import androidx.paging.PagingData
import com.example.danew_app.data.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsByCategory(category: String): Flow<PagingData<NewsEntity>>
    suspend fun getNewsById(id: String): Flow<NewsEntity>
    suspend fun getNewsBySearchQuery(searchQuery: String): Flow<PagingData<NewsEntity>>
}

