package com.example.danew_app.domain.repository

import com.example.danew.data.local.entity.UserEntity


interface UserRepository {
    suspend fun insertUser(user:UserEntity)
}