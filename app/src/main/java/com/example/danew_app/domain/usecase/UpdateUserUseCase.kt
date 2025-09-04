package com.example.danew_app.domain.usecase

import com.example.danew_app.data.dto.UpdateUserRequest
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.UserModel
import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(token:String, updateUserRequest: UpdateUserRequest):UserModel{
        return userRepository.updateUser(token, updateUserRequest).toDomain()
    }
}