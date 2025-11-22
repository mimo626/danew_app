package com.example.danew_app.domain.repository

import com.example.danew_app.data.entity.SearchHistoryEntity
import com.example.danew_app.data.local.SearchHistoryDao
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val dao: SearchHistoryDao
) {
    suspend fun getRecentSearches(userId:String) = dao.getRecentSearches(userId)
    suspend fun insertSearch(searchHistory: SearchHistoryEntity) = dao.insertSearch(searchHistory)
    suspend fun deleteSearch(userId: String, keyword: String) = dao.deleteSearch(userId, keyword)
    suspend fun clearAll(userId: String) = dao.clearAll(userId)
}
