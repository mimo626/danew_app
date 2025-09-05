package com.example.danew_app.domain.repository

import com.example.danew_app.data.entity.SearchHistoryEntity
import com.example.danew_app.data.local.SearchHistoryDao
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val dao: SearchHistoryDao
) {
    suspend fun getRecentSearches() = dao.getRecentSearches()
    suspend fun insertSearch(keyword: String) = dao.insertSearch(SearchHistoryEntity(keyword = keyword))
    suspend fun deleteSearch(keyword: String) = dao.deleteSearch(keyword)
    suspend fun clearAll() = dao.clearAll()
}
