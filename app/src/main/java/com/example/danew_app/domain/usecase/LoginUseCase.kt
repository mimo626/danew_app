package com.example.danew_app.domain.usecase

import android.util.Log
import com.example.danew_app.data.dto.LoginResponse
import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(userId: String, password:String) : LoginResponse{
        Log.d("User", "LoginUseCase_invoke: $userId")
        return repository.login(userId, password)
    }
}