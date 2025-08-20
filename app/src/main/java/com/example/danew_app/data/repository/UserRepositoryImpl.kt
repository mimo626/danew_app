package com.example.danew_app.data.repository

import android.util.Log
import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.data.remote.UserApi
import com.example.danew_app.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {
    override suspend fun insertUser(user: UserEntity) {
        Log.d("User", "UserRepositoryImpl_insertUser: $user")
        api.insert(user)
    }
}
