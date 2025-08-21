package com.example.danew_app.data.repository

import android.util.Log
import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.data.remote.UserApi
import com.example.danew_app.domain.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {
    override suspend fun insertUser(user: UserEntity) {
        Log.d("User", "UserRepositoryImpl_insertUser: $user")
        api.save(user).enqueue(object : Callback<UserEntity> {
            override fun onResponse(call: Call<UserEntity>, response: Response<UserEntity>) {
                Log.i("signup", "성공: ${response.body()}")
            }

            override fun onFailure(call: Call<UserEntity>, t: Throwable) {
                Log.e("signup", "실패", t)
            }
        })
    }
}
