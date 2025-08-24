package com.example.danew_app.data.repository

import android.util.Log
import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.data.dto.LoginRequest
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

    override suspend fun login(userId: String, password: String) {
        val request = LoginRequest(userId, password)
        api.login(request).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.i("login", "로그인 성공: ${response.body()}")
                } else {
                    Log.w("login", "로그인 실패: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("login", "로그인 에러", t)
            }
        })
    }

    override suspend fun checkUserId(userId: String, callback: (Boolean) -> Unit) {
        api.checkUserId(userId).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.body() == true) {
                    // 사용 가능
                    callback(true)
                    Log.i("User", "UserRepositoryImpl 아이디 사용 가능: ${response.body()}")
                } else {
                    // 중복 (409)
                    callback(false)
                    Log.e("User", "UserRepositoryImpl 아이디 중복: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                // 네트워크 에러 → false 로 처리할지, 별도 분기할지는 정책에 따라 다름
                callback(false)
            }
        })
    }


}
