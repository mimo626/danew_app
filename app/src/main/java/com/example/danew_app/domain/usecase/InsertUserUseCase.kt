package com.example.danew_app.domain.usecase

import UserModel
import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.data.mapper.toEntity
import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(userModel: UserModel): UserEntity {
        return repository.insertUser(userModel.toEntity())
    }
}