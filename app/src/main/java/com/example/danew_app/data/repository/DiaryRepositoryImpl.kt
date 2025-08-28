package com.example.danew_app.data.repository

import android.util.Log
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

@Singleton
class DiaryRepositoryImpl @Inject constructor(
    private val api: DiaryApi,
) : DiaryRepository{

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun saveDiary(content: String, createdAt: String, token: String): DiaryEntity =
        suspendCancellableCoroutine{ cont ->
            api.save(DiaryRequest(content, createdAt), token).enqueue(object : Callback<DiaryEntity>{
                override fun onResponse(call: Call<DiaryEntity>, response: Response<DiaryEntity>) {
                    if (response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            cont.resume(body){}
                            Log.i("Diary 저장", "${response.body()}")

                        }
                        else {
                            Log.e("Diary 저장", "응답 바디 없음: ${response.code()}")
                        }
                    } else {
                        Log.e("Diary 저장", "서버 오류: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<DiaryEntity>, t: Throwable) {
                    Log.e("Diary 저장", "네트워크 실패", t)
                }
            })
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getByDate(token: String, createdAt: String): DiaryEntity? =
        suspendCancellableCoroutine{ cont ->
            api.getByDate(token, createdAt).enqueue(object : Callback<DiaryEntity?>{
                override fun onResponse(call: Call<DiaryEntity?>, response: Response<DiaryEntity?>) {
                    if (response.isSuccessful){
                        val body = response.body()
                        if (body != null){
                            cont.resume(body) {}
                            Log.i("Diary 날짜별 조회", "성공: ${body}")
                        } else{
                            cont.resume(null) {} // 실패 시 null 처리
                            Log.e("Diary 날짜별 조회", "응답 바디 없음: ${response.code()}")
                        }
                    }else {
                        Log.e("Diary 날짜별 조회", "서버 오류: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<DiaryEntity?>, t: Throwable) {
                    cont.resume(null) {} // 실패 시 null 처리
                    Log.e("Diary 날짜별 조회", "네트워크 실패", t)
                }
            }
            )

    }
}