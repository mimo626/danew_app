package com.example.danew_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.danew_app.data.entity.NewsSummaryEntity

@Dao
interface NewsSummaryDao {

    @Query("SELECT * FROM news_summary WHERE articleId = :id LIMIT 1")
    suspend fun getSummarizedNews(id: String): NewsSummaryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsSummary(summary: NewsSummaryEntity)

    @Query("DELETE FROM news_summary")
    suspend fun deleteAllSummaries()
}
