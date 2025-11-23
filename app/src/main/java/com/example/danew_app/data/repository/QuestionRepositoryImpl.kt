package com.example.danew_app.data.repository

import android.util.Log
import com.example.danew_app.data.dto.ApiResponse
import com.example.danew_app.data.dto.QuestionRequest
import com.example.danew_app.data.entity.QuestionEntity
import com.example.danew_app.data.remote.QuestionApi
import com.example.danew_app.domain.repository.QuestionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resumeWithException

@Singleton
class QuestionRepositoryImpl @Inject constructor(
    private val api: QuestionApi
) : QuestionRepository {

    override suspend fun saveQuestion(questionRequest: QuestionRequest): QuestionEntity =
        suspendCancellableCoroutine { cont ->
            api.saveQuestion(questionRequest).enqueue(object : Callback<ApiResponse<QuestionEntity>> {
                override fun onResponse(
                    call: Call<ApiResponse<QuestionEntity>>,
                    response: Response<ApiResponse<QuestionEntity>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        if (body.status == "success" && body.data != null) {
                            val question = body.data
                            cont.resume(question) {}
                            Log.i("Question 저장", "$question")
                        } else {
                            val msg = body.message ?: "FAQ 저장 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("Question 저장", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "FAQ 저장 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Question 저장", "실패: $errorMsg")
                    }
                }
                override fun onFailure(call: Call<ApiResponse<QuestionEntity>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Question 저장", "네트워크 실패", t)
                }
            })
        }

    override suspend fun getQuestionList(): List<QuestionEntity> =
        suspendCancellableCoroutine { cont ->
            api.getQuestionList().enqueue(object : Callback<ApiResponse<List<QuestionEntity>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<QuestionEntity>>>,
                    response: Response<ApiResponse<List<QuestionEntity>>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        if (body.status == "success" && body.data != null) {
                            val list = body.data
                            cont.resume(list) {}
                            Log.i("Question 목록 조회", "크기: ${list.size}")
                        } else {
                            val msg = body.message ?: "FAQ 목록 조회 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("Question 목록 조회", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "FAQ 목록 조회 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Question 목록 조회", "실패: $errorMsg")
                    }
                }
                override fun onFailure(call: Call<ApiResponse<List<QuestionEntity>>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Question 목록 조회", "네트워크 실패", t)
                }
            })
        }

    override suspend fun deleteQuestion(questionId: String): String =
        suspendCancellableCoroutine { cont ->
            api.deleteQuestion(questionId).enqueue(object : Callback<ApiResponse<String>> {
                override fun onResponse(
                    call: Call<ApiResponse<String>>,
                    response: Response<ApiResponse<String>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        if (body.status == "success" && body.data != null) {
                            val id = body.data
                            cont.resume(id) {}
                            Log.i("Question 삭제", "ID: $id")
                        } else {
                            val msg = body.message ?: "FAQ 삭제 실패"
                            cont.resumeWithException(RuntimeException(msg))
                            Log.e("Question 삭제", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "FAQ 삭제 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Question 삭제", "실패: $errorMsg")
                    }
                }
                override fun onFailure(call: Call<ApiResponse<String>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Question 삭제", "네트워크 실패", t)
                }
            })
        }
}