package com.example.danew_app.domain.usecase

import android.util.Log
import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(userId: String, password:String){
        Log.d("User", "LoginUseCase_invoke: $userId")
        repository.login(userId, password)
    }
}