package com.example.danew_app.data.repository

import android.util.Log
import com.example.danew_app.data.remote.NotificationApi // API 위치에 맞게 import 수정 필요
import com.example.danew_app.domain.repository.NotificationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resumeWithException

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val api: NotificationApi,
) : NotificationRepository {

    override suspend fun sendNotification(token: String, title: String, content:String): String =
        suspendCancellableCoroutine { cont ->
            // Retrofit의 enqueue를 사용하여 비동기 호출 시작
            api.sendSelfNotification(token, title, content).enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    // 성공적으로 응답이 왔을 때
                    if (response.isSuccessful) {
                        val body = response.body()
                        // body가 null이 아닌 경우 (String 응답이 있는 경우)
                        if (body != null) {
                            Log.i("FCM 전송", "성공: $body")
                            cont.resume(body) {} // 코루틴 재개 및 결과 반환
                        } else {
                            // 성공은 했으나 Body가 비어있는 경우
                            val msg = "알림 전송 성공했으나 응답 없음"
                            Log.w("FCM 전송", msg)
                            cont.resume(msg) {} // 혹은 예외처리
                        }
                    } else {
                        // 서버가 4xx, 5xx 에러를 보낸 경우
                        val errorMsg = response.errorBody()?.string() ?: "알림 전송 실패 (Unknown Error)"
                        Log.e("FCM 전송", "실패: $errorMsg")
                        cont.resumeWithException(RuntimeException(errorMsg))
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // 네트워크 통신 자체가 실패한 경우
                    Log.e("FCM 전송", "네트워크 오류", t)
                    cont.resumeWithException(t)
                }
            })
        }
}