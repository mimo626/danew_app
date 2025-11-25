package com.example.danew_app.domain.usecase

import com.example.danew_app.data.dto.LoginRequest
import com.example.danew_app.data.dto.UserResponse
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository,
    private val dataSource: UserDataSource,
) {
    suspend operator fun invoke(loginRequest: LoginRequest): UserResponse {
        val response = repository.login(loginRequest)

        if (response.token.isNotEmpty()) {
            dataSource.saveLoginInfo(response.token)
        }

        return response
    }

    suspend fun isUserLoggedIn(): Boolean {
        return dataSource.checkLoginState()
    }
}
