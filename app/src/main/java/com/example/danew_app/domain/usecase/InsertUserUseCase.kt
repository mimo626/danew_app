package com.example.danew_app.domain.usecase

import UserModel
import android.util.Log
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.data.mapper.toEntity
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(userModel: UserModel){
        Log.d("User", "InsertUserUseCase_invoke: $userModel")
        repository.insertUser(userModel.toEntity())
    }
}