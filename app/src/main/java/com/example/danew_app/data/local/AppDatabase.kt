package com.example.danew_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.danew_app.data.entity.NewsSummaryEntity
import com.example.danew_app.data.entity.SearchHistoryEntity
import com.example.danew_app.data.entity.TodayNewsEntity

@Database(
    entities = [
        SearchHistoryEntity::class,
        TodayNewsEntity::class,
        NewsSummaryEntity::class,
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun todayNewsDao(): TodayNewsDao
    abstract fun newsSummaryDao(): NewsSummaryDao
}
