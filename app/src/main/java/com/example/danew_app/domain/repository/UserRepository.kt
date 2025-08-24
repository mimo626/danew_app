package com.example.danew_app.domain.repository

import com.example.danew.data.local.entity.UserEntity


interface UserRepository {
    suspend fun insertUser(user:UserEntity)
    suspend fun login(userId: String, password: String)
    suspend fun checkUserId(userId: String, callback: (Boolean) -> Unit)
}