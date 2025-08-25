package com.example.danew_app.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.lifecycleScope
import com.example.danew_app.presentation.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val userViewModel: SignupViewModel by viewModels()

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
