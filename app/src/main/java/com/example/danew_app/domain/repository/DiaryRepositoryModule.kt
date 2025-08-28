package com.example.danew_app.domain.repository

import com.example.danew_app.data.repository.DiaryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DiaryRepositoryModule {

    @Binds
    abstract fun bindDiaryRepository(
        impl: DiaryRepositoryImpl
    ):DiaryRepository
}