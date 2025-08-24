package com.example.danew_app.domain.usecase

import UserModel
import android.util.Log
import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.data.mapper.toEntity
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(userModel: UserModel): UserEntity {
        return repository.insertUser(userModel.toEntity())
    }
}