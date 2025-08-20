package com.example.danew_app.domain.repository

import com.example.danew_app.data.entity.NewsEntity

interface NewsRepository {
    suspend fun getNewsByCategory(category: String, loadMore:Boolean): List<NewsEntity>
    suspend fun getNewsById(id: String): List<NewsEntity>
    suspend fun getNewsBySearchQuery(searchQuery: String, loadMore:Boolean): List<NewsEntity>
}

