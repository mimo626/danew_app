package com.example.danew_app.presentation.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.danew.presentation.main.MainScreen
import com.example.danew_app.ui.theme.Danew_appTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isLoggedIn = intent.getBooleanExtra("isLoggedIn", false)

//        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w("FCM", "토큰 가져오기 실패", task.exception)
//                return@addOnCompleteListener
//            }
//
//            // 현재 단말기의 FCM 토큰 가져옴
//            val token = task.result
//            Log.d("FCM", "Current Token: $token")
//
//            // 이 토큰을 복사해서 Postman이나 서버 API 테스트에 사용하세요.
//        }
        setContent {
            val navHostController = rememberNavController()

            Danew_appTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(navHostController = navHostController, isLoggedIn = isLoggedIn)
                }
            }
        }
    }
}