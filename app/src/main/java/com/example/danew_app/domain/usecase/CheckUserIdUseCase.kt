package com.example.danew_app.domain.usecase

import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject

class CheckUserIdUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(userId: String, callback: (Boolean) -> Unit){
        repository.checkUserId(userId, callback)
    }
}

