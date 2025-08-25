package com.example.danew_app.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.rememberNavController
import com.example.danew.core.navigation.BottomNavItem
import com.example.danew.presentation.main.MainScreen
import com.example.danew_app.presentation.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            val isLoggedIn by userViewModel.isLoggedIn.observeAsState(false)

            //TODO 스플래쉬 화면 구현 후 적용
//            if (isLoggedIn == null) {
//                // 아직 로그인 체크 중이면 Splash 보여주기
//                SplashScreen()
//            } else {
//                MainScreen(navHostController = navHostController, isLoggedIn = isLoggedIn!!)
//            }
            MainScreen(navHostController = navHostController, isLoggedIn = isLoggedIn)

            LaunchedEffect(isLoggedIn) {
                if (isLoggedIn) {
                    navHostController.navigate(BottomNavItem.Home.route) {
                        popUpTo("start") { inclusive = true }
                    }
                } else {
                    navHostController.navigate("start") {
                        popUpTo(BottomNavItem.Home.route) { inclusive = true }
                    }
                }
            }

        }

        // ✅ 앱 시작 시 로그인 상태 확인
        userViewModel.checkLoginState()
    }
}