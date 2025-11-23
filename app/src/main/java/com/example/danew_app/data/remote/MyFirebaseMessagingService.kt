package com.example.danew_app.data.remote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log
import com.example.danew_app.R
import com.example.danew_app.presentation.main.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New Token: $token")
        sendRegistrationToServer(token)

        // [추가된 부분] 앱 설치 시 혹은 토큰 갱신 시 주제 구독
        // 이 코드는 MainActivity의 onCreate 같은 곳에 넣어도 됩니다.
        FirebaseMessaging.getInstance().subscribeToTopic("daily_routine")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "주제 구독 성공: daily_routine")
                } else {
                    Log.w("FCM", "주제 구독 실패", task.exception)
                }
            }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM_TEST", "메시지 수신함: ${remoteMessage.notification?.body}")

        super.onMessageReceived(remoteMessage)

        remoteMessage.notification?.let {
            showNotification(it.title, it.body)
        }
    }

    private fun showNotification(title: String?, body: String?) {
        val channelId = "daily_news_channel" // 채널 ID 분리 가능
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "뉴스 및 일기 알림", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java)
        // 알림 클릭 시 특정 탭(뉴스 탭 등)으로 이동하려면 intent에 extra 데이터 추가 가능
        intent.putExtra("type", "daily_routine")

        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun sendRegistrationToServer(token: String) {
        // TODO: 서버 전송 로직
    }
}