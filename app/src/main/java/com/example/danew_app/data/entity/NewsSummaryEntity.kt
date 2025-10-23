package com.example.danew_app.data.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_summary")
data class NewsSummaryEntity(
    @PrimaryKey val articleId: String,
    val summary: String
)
