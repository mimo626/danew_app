package com.example.danew.core.navigation

import LoginScreen
import MainScreen
import NewsDetailScreen
import ProfileEditScreen
import SearchResultScreen
import SearchScreen
import SignupAddScreen
import SignupFinishScreen
import SignupScreen
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.danew.presentation.bookmark.BookmarkScreen
import com.example.danew.presentation.category.CategoryScreen
import com.example.danew.presentation.diary.DiaryScreen
import com.example.danew.presentation.diary.DiaryWriteScreen
import com.example.danew.presentation.home.AlarmScreen
import com.example.danew.presentation.home.HomeScreen
import com.example.danew.presentation.home.SettingScreen
import com.example.danew.presentation.home.StartScreen
import com.example.danew.presentation.profile.MyPageScreen
import com.example.danew_app.presentation.login.KeywordScreen
import com.example.danew_app.presentation.viewmodel.SignupViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun BottomNavGraph(navHostController: NavHostController, modifier: Modifier) {

    NavHost(navHostController, startDestination = "start", modifier = modifier) {
        composable(BottomNavItem.Home.route) { HomeScreen(navHostController) }
        composable(BottomNavItem.Category.route) { CategoryScreen(navHostController) }
        composable(BottomNavItem.Diary.route) { DiaryScreen(navHostController) }
        composable(BottomNavItem.Bookmark.route) { BookmarkScreen(navHostController) }
        composable(BottomNavItem.My.route) { MyPageScreen(navHostController) }

        // 메인 탭 네비게이션
        composable("main") {
            MainScreen(navHostController = navHostController) // 내부에서 BottomNavGraph 호출
        }

        // BottomNav 없는 화면들
        composable("start") {
            StartScreen(navHostController = navHostController)
        }

        composable("login") {
            LoginScreen(navHostController = navHostController)
        }

        // 회원가입 플로우 네비게이션 그래프
        navigation(startDestination = "signup", route = "signupFlow") {
            composable("signup") { backStackEntry ->
                val viewModel: SignupViewModel =
                    hiltViewModel(navHostController.getBackStackEntry("signupFlow"))
                SignupScreen(navHostController, viewModel)
            }
            composable("signupAdd") { backStackEntry ->
                val viewModel: SignupViewModel =
                    hiltViewModel(navHostController.getBackStackEntry("signupFlow"))
                SignupAddScreen(navHostController, viewModel)
            }
            composable("keyword") { backStackEntry ->
                val viewModel: SignupViewModel =
                    hiltViewModel(navHostController.getBackStackEntry("signupFlow"))
                KeywordScreen(navHostController, viewModel)
            }
            composable("signupFinish") { backStackEntry ->
                val viewModel: SignupViewModel =
                    hiltViewModel(navHostController.getBackStackEntry("signupFlow"))
                SignupFinishScreen(navHostController, viewModel)
            }
        }
        
        composable("details/{newsId}") { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId")
            newsId?.let { NewsDetailScreen(it, navHostController) }
        }

        composable("diary/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            date?.let { DiaryWriteScreen(it, navHostController) }
        }

        composable("search") {
            SearchScreen(navHostController = navHostController)
        }

        composable("search/{query}") { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query")
            query?.let { SearchResultScreen(it, navHostController) }
        }

        composable("profileEdit") {
            ProfileEditScreen(navHostController = navHostController)
        }

        composable("alarm") {
            AlarmScreen(navHostController = navHostController)
        }

        composable("settings") {
            SettingScreen(navHostController = navHostController)
        }
    }
}
