package com.example.danew_app.data.repository

import android.util.Log
import com.example.danew_app.data.dto.AnnounceRequest
import com.example.danew_app.data.dto.ApiResponse
import com.example.danew_app.data.entity.AnnounceEntity
import com.example.danew_app.data.remote.AnnounceApi
import com.example.danew_app.domain.repository.AnnounceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resumeWithException

@Singleton
class AnnounceRepositoryImpl @Inject constructor(
    private val api: AnnounceApi
) : AnnounceRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun saveAnnounce(announceRequest: AnnounceRequest): AnnounceEntity =
        suspendCancellableCoroutine { cont ->
            api.saveAnnounce(announceRequest).enqueue(object : Callback<ApiResponse<AnnounceEntity>> {
                override fun onResponse(
                    call: Call<ApiResponse<AnnounceEntity>>,
                    response: Response<ApiResponse<AnnounceEntity>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        if (body.status == "success" && body.data != null) {
                            val announce = body.data
                            cont.resume(announce) {}
                            Log.i("Announce 저장", "$announce")
                        } else {
                            val msg = body.message ?: "공지사항 저장 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("Announce 저장", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "공지사항 저장 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Announce 저장", "실패: $errorMsg")
                    }
                }

                override fun onFailure(call: Call<ApiResponse<AnnounceEntity>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Announce 저장", "네트워크 실패", t)
                }
            })
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAnnounceList(): List<AnnounceEntity> =
        suspendCancellableCoroutine { cont ->
            api.getAnnounceList().enqueue(object : Callback<ApiResponse<List<AnnounceEntity>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<AnnounceEntity>>>,
                    response: Response<ApiResponse<List<AnnounceEntity>>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        if (body.status == "success" && body.data != null) {
                            val list = body.data
                            cont.resume(list) {}
                            Log.i("Announce 목록 조회", "크기: ${list.size}")
                        } else {
                            val msg = body.message ?: "공지사항 목록 조회 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("Announce 목록 조회", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "공지사항 목록 조회 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Announce 목록 조회", "실패: $errorMsg")
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<AnnounceEntity>>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Announce 목록 조회", "네트워크 실패", t)
                }
            })
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun deleteAnnounce(announceId: String): String =
        suspendCancellableCoroutine { cont ->
            api.deleteAnnounce(announceId).enqueue(object : Callback<ApiResponse<String>> {
                override fun onResponse(
                    call: Call<ApiResponse<String>>,
                    response: Response<ApiResponse<String>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        if (body.status == "success" && body.data != null) {
                            val id = body.data
                            cont.resume(id) {}
                            Log.i("Announce 삭제", "ID: $id")
                        } else {
                            val msg = body.message ?: "공지사항 삭제 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("Announce 삭제", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "공지사항 삭제 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Announce 삭제", "실패: $errorMsg")
                    }
                }

                override fun onFailure(call: Call<ApiResponse<String>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Announce 삭제", "네트워크 실패", t)
                }
            })
        }
}