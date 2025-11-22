package com.example.danew_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.danew_app.data.entity.SearchHistoryEntity

@Dao
interface SearchHistoryDao {
    // 특정 유저(userId)의 기록만 가져오기
    @Query("SELECT * FROM search_history WHERE userId = :userId ORDER BY timestamp DESC LIMIT 10")
    suspend fun getRecentSearches(userId: String): List<SearchHistoryEntity>

    // 저장 (이미 Entity 안에 userId가 포함되어 있음)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchHistory: SearchHistoryEntity)

    // 특정 유저의 특정 키워드만 삭제
    @Query("DELETE FROM search_history WHERE userId = :userId AND keyword = :keyword")
    suspend fun deleteSearch(userId: String, keyword: String)

    // 특정 유저의 기록만 전체 삭제 (다른 유저 기록 보호)
    @Query("DELETE FROM search_history WHERE userId = :userId")
    suspend fun clearAll(userId: String)
}