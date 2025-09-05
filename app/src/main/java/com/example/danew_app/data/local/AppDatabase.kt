package com.example.danew_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.danew_app.data.entity.SearchHistoryEntity

@Database(entities = [SearchHistoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}
