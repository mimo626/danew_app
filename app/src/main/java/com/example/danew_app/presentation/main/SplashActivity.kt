package com.example.danew_app.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.danew_app.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen() // Compose 로 간단한 로딩 UI
            userViewModel.checkLoginState()
            val isLoggedIn by userViewModel.isLoggedIn.observeAsState(false)

            navigateNext(isLoggedIn)
        }
    }

    private fun navigateNext(isLoggedIn: Boolean) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("isLoggedIn", isLoggedIn)
        startActivity(intent)
        finish() // SplashActivity 는 종료
    }
}
