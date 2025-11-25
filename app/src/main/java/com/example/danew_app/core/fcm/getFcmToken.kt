package com.example.danew_app.core.fcm

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

// Firebase Task를 Coroutine으로 변환해서 토큰을 반환하는 함수
suspend fun getFcmToken(): String? {
    return try {
        FirebaseMessaging.getInstance().token.await()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}