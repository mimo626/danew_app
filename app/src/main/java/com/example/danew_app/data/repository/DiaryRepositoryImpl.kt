package com.example.danew_app.data.repository

import android.util.Log
import com.example.danew_app.data.dto.ApiResponse
import com.example.danew_app.data.dto.DiaryRequest
import com.example.danew_app.data.entity.DiaryEntity
import com.example.danew_app.data.remote.DiaryApi
import com.example.danew_app.domain.repository.DiaryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resumeWithException

@Singleton
class DiaryRepositoryImpl @Inject constructor(
    private val api: DiaryApi,
) : DiaryRepository{

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun saveDiary(content: String, createdAt: String, token: String): DiaryEntity =
        suspendCancellableCoroutine{ cont ->
            api.save(DiaryRequest(content, createdAt), token).enqueue(object : Callback<ApiResponse<DiaryEntity>>{
                override fun onResponse(call: Call<ApiResponse<DiaryEntity>>, response: Response<ApiResponse<DiaryEntity>>) {
                    val body = response.body()
                    if (response.isSuccessful && body != null){
                        if (body.status == "success" && body.data != null) {
                            val diary = body.data
                            cont.resume(diary){}
                            Log.i("Diary 저장", "${diary}")

                        }
                        else {
                            val msg = body.message ?: "다이어리 저장 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("Diary 저장", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "다이어리 저장 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Diary 저장", "실패: $errorMsg")                    }
                }

                override fun onFailure(call: Call<ApiResponse<DiaryEntity>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Diary 저장", "네트워크 실패", t)
                }
            })
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getByDate(token: String, createdAt: String): DiaryEntity? =
        suspendCancellableCoroutine{ cont ->
            api.getByDate(token, createdAt).enqueue(object : Callback<ApiResponse<DiaryEntity?>>{
                override fun onResponse(call: Call<ApiResponse<DiaryEntity?>>, response: Response<ApiResponse<DiaryEntity?>>) {
                    val body = response.body()
                    if (response.isSuccessful && body != null){
                        if (body.status == "success" && body.data != null) {
                            val diary = body.data
                            cont.resume(diary){}
                            Log.i("Diary 날짜별 조회", "${diary}")

                        }
                        else {
                            val msg = body.message ?: "다이어리 날짜별 조회 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("Diary 날짜별 조회", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "다이어리 날짜별 조회 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Diary 날짜별 조회", "실패: $errorMsg")                    }
                }

                override fun onFailure(call: Call<ApiResponse<DiaryEntity?>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Diary 날짜별 조회", "네트워크 실패", t)
                }
            })
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun updateDiary(token:String, diaryId:String, diaryRequest: DiaryRequest): DiaryEntity =
        suspendCancellableCoroutine { cont ->
            api.updateDiary(token, diaryId, diaryRequest).enqueue(object : Callback<ApiResponse<DiaryEntity>>{
                override fun onResponse(call: Call<ApiResponse<DiaryEntity>>, response: Response<ApiResponse<DiaryEntity>>) {
                    val body = response.body()
                    if (response.isSuccessful && body != null){
                        if (body.status == "success" && body.data != null) {
                            val diary = body.data
                            cont.resume(diary){}
                            Log.i("Diary 수정", "${diary}")

                        }
                        else {
                            val msg = body.message ?: "다이어리 수정 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("Diary 수정", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "다이어리 수정 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Diary 수정", "실패: $errorMsg")                    }
                }

                override fun onFailure(call: Call<ApiResponse<DiaryEntity>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Diary 수정", "네트워크 실패", t)
                }
            })
        }
}