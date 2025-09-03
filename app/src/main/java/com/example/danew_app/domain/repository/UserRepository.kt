package com.example.danew_app.domain.repository

import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.data.dto.UserResponse


interface UserRepository {
    suspend fun insertUser(user:UserEntity):UserEntity
    suspend fun login(userId: String, password: String) : UserResponse
    suspend fun checkUserId(userId: String):Boolean
    suspend fun getUser(token: String,): UserEntity
}