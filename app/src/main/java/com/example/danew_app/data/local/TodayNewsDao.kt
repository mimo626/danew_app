package com.example.danew_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.danew_app.data.entity.TodayNewsEntity

@Dao
interface TodayNewsDao {

    @Query("SELECT * FROM today_news WHERE savedAt = :date ORDER BY id DESC")
    suspend fun getTodayNews(date: String): List<TodayNewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: TodayNewsEntity)

    @Query("DELETE FROM today_news WHERE savedAt != :date")
    suspend fun clearOldNews(date: String)

    @Query("DELETE FROM today_news")
    suspend fun clearAll()
}
