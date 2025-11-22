package com.example.danew_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.danew_app.data.entity.TodayNewsEntity

@Dao
interface TodayNewsDao {

    // 수정됨: 날짜(String) 대신 시작시간과 끝시간(Long) 범위를 받습니다.
    // "오늘 0시 ~ 오늘 23시 59분 사이에 저장된 뉴스"를 조회
    @Query("SELECT * FROM today_news WHERE userId = :userId AND savedAt >= :startTime AND savedAt <= :endTime ORDER BY savedAt DESC")
    suspend fun getTodayNewsList(userId: String, startTime: Long, endTime: Long): List<TodayNewsEntity>

    @Query("SELECT * FROM today_news WHERE newsId = :newsId AND userId = :userId")
    suspend fun getTodayNews(userId: String, newsId: String): TodayNewsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: TodayNewsEntity)

    // 수정됨: 특정 시간(예: 오늘 0시)보다 이전에 저장된 뉴스는 모두 삭제
    @Query("DELETE FROM today_news WHERE savedAt < :thresholdTime")
    suspend fun clearOldNews(thresholdTime: Long)

    @Query("DELETE FROM today_news WHERE userId = :userId")
    suspend fun clearAll(userId: String)
}