package com.example.danew_app.domain.usecase

import com.example.danew_app.data.dto.UserResponse
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository,
    private val dataSource: UserDataSource,   // ✅ 추가
) {
    suspend operator fun invoke(userId: String, password: String): UserResponse {
        val response = repository.login(userId, password)

        if (response.token.isNotEmpty()) {
            dataSource.saveLoginInfo(response.token)
        }

        return response
    }

    suspend fun isUserLoggedIn(): Boolean {
        return dataSource.checkLoginState()
    }
}
