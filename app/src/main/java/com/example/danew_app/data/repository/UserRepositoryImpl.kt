package com.example.danew_app.data.repository

import android.util.Log
import com.example.danew.data.local.entity.UserEntity
import com.example.danew_app.data.dto.ApiResponse
import com.example.danew_app.data.dto.LoginRequest
import com.example.danew_app.data.dto.UpdateUserRequest
import com.example.danew_app.data.dto.UserResponse
import com.example.danew_app.data.remote.UserApi
import com.example.danew_app.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resumeWithException

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi,
) : UserRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun insertUser(user: UserEntity): UserEntity =
        suspendCancellableCoroutine { cont ->
            Log.d("User", "UserRepositoryImpl_insertUser: $user")

            api.save(user).enqueue(object : Callback<ApiResponse<UserResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<UserResponse>>,
                    response: Response<ApiResponse<UserResponse>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        if (body.status == "success" && body.data != null) {
                            // 성공: UserResponse를 UserEntity로 변환
                            val userEntity = UserEntity(
                                userId = body.data.userId,
                                createdAt = body.data.createdAt,
                                password = user.password, // 필요한 필드 채우기
                                name = user.name,
                                age = user.age,
                                gender = user.gender
                            )
                            cont.resume(userEntity) {}
                            Log.i("User 회원가입", "성공: $userEntity")
                        } else {
                            // 서버에서 status가 error인 경우
                            val msg = body.message ?: "회원가입 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("User 회원가입", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "회원가입 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("User 회원가입", "실패: $errorMsg")
                    }
                }

                override fun onFailure(call: Call<ApiResponse<UserResponse>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("User 회원가입", "네트워크 실패", t)
                }
            })
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun login(userId: String, password: String): UserResponse =
        suspendCancellableCoroutine { cont ->
            api.login(LoginRequest(userId, password)).enqueue(object : Callback<ApiResponse<UserResponse>> {
                override fun onResponse(
                    call: Call<ApiResponse<UserResponse>>,
                    response: Response<ApiResponse<UserResponse>>) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        if (body.status == "success" && body.data != null) {
                            val userResponse = body.data
                            cont.resume(userResponse) {}
                            Log.i("User 로그인", "성공: ${userResponse}")
                        } else {
                            // 서버에서 status가 error인 경우
                            val msg = body.message ?: "로그인 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("User 로그인", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "로그인 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("User 로그인", "실패: $errorMsg")
                    }
                }

                override fun onFailure(call: Call<ApiResponse<UserResponse>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("User 로그인", "네트워크 실패", t)
                }
            })
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getUser(token: String,): UserEntity =
        suspendCancellableCoroutine { cont ->
            api.getUser(token).enqueue(object : Callback<ApiResponse<UserEntity>> {
                override fun onResponse(call: Call<ApiResponse<UserEntity>>, response: Response<ApiResponse<UserEntity>>) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        if (body.status == "success" && body.data != null) {
                            val user = body.data
                            cont.resume(user) {}
                            Log.i("User 조회", "성공: ${user}")
                        } else {
                            // 서버에서 status가 error인 경우
                            val msg = body.message ?: "유저 조회 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("User 조회", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "로그인 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("User 조회", "실패: $errorMsg")
                    }
                }

                override fun onFailure(call: Call<ApiResponse<UserEntity>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("User 로그인", "네트워크 실패", t)
                }
            })
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun updateUser(token: String, updateUserRequest: UpdateUserRequest): UserEntity =
        suspendCancellableCoroutine { cont ->
            api.updateUser(token, updateUserRequest).enqueue(object : Callback<ApiResponse<UserEntity>> {
                override fun onResponse(call: Call<ApiResponse<UserEntity>>, response: Response<ApiResponse<UserEntity>>) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        if (body.status == "success" && body.data != null) {
                            val user = body.data
                            cont.resume(user) {}
                            Log.i("User 수정", "성공: ${user}")
                        } else {
                            // 서버에서 status가 error인 경우
                            val msg = body.message ?: "유저 수정 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("User 수정", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "유저 수정 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("User 수정", "실패: $errorMsg")
                    }
                }

                override fun onFailure(call: Call<ApiResponse<UserEntity>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("User 수정", "네트워크 실패", t)
                }
            })
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun checkUserId(userId: String) :Boolean =
        suspendCancellableCoroutine{ cont ->
        api.checkUserId(userId).enqueue(object : Callback<ApiResponse<Boolean>> {
            override fun onResponse(call: Call<ApiResponse<Boolean>>, response: Response<ApiResponse<Boolean>>) {
                val body = response.body()
                Log.i("User 아이디 중복체크", "응답: ${body}")

                if (response.isSuccessful && body != null) {
                    if (body.status == "success" && body.data != null) {
                        val isCheck = body.data
                        cont.resume(isCheck) {}
                        Log.i("User 아이디 중복체크", "성공: ${isCheck}")
                    } else {
                        // 서버에서 status가 error인 경우
                        val msg = body.message ?: "유저 조회 실패"
                        cont.resumeWithException(RuntimeException(msg))
                        Log.e("User 아이디 중복체크", "실패: $msg")
                    }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "아이디 중복체크 실패"
                    cont.resumeWithException(RuntimeException(errorMsg))
                    Log.e("User 아이디 중복체크", "실패: $errorMsg")
                }
            }

            override fun onFailure(call: Call<ApiResponse<Boolean>>, t: Throwable) {
                cont.resumeWithException(t)
                Log.e("User 아이디 중복체크", "네트워크 실패", t)
            }
        })
    }
}
