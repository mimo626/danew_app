package com.example.danew_app.domain.repository

import com.example.danew_app.data.repository.AnnounceRepositoryImpl
import com.example.danew_app.data.repository.NotificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationRepositoryModule {
    @Binds
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl
    ):NotificationRepository
}