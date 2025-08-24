package com.example.danew_app.domain.repository

import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.data.dto.LoginResponse


interface UserRepository {
    suspend fun insertUser(user:UserEntity)
    suspend fun login(userId: String, password: String) : LoginResponse
    suspend fun checkUserId(userId: String, callback: (Boolean) -> Unit)
}