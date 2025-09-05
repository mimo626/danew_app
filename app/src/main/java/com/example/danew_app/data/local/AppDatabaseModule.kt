package com.example.danew_app.data.local

import android.content.Context
import androidx.room.Room
import com.example.danew_app.data.local.AppDatabase
import com.example.danew_app.data.local.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideSearchHistoryDao(db: AppDatabase): SearchHistoryDao {
        return db.searchHistoryDao()
    }
}
