package com.example.danew_app.domain.repository

import com.example.danew_app.data.repository.NewsRepositoryImpl
import com.example.danew_app.domain.model.NewsModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

interface NewsRepository {
    suspend fun getNewsByCategory(category: String, loadMore:Boolean): List<NewsModel>
    suspend fun getNewsById(id: String): List<NewsModel>
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNewsRepository(
        impl: NewsRepositoryImpl
    ): NewsRepository
}
