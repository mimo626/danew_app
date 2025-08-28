package com.example.danew.core.navigation



import SearchResultScreen
import SearchScreen
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.danew.presentation.bookmark.BookmarkScreen
import com.example.danew.presentation.category.CategoryScreen
import com.example.danew.presentation.diary.DiaryScreen
import com.example.danew.presentation.diary.DiaryWriteScreen
import com.example.danew.presentation.home.AlarmScreen
import com.example.danew.presentation.home.HomeScreen
import com.example.danew.presentation.home.NewsDetailScreen
import com.example.danew.presentation.home.SettingScreen
import com.example.danew.presentation.home.StartScreen
import com.example.danew.presentation.login.LoginScreen
import com.example.danew.presentation.login.SignupAddScreen
import com.example.danew.presentation.login.SignupFinishScreen
import com.example.danew.presentation.login.SignupScreen
import com.example.danew.presentation.profile.MyPageScreen
import com.example.danew.presentation.profile.ProfileEditScreen
import com.example.danew_app.domain.model.DiaryModel
import com.example.danew_app.presentation.login.KeywordScreen
import com.example.danew_app.presentation.viewmodel.SignupViewModel
import com.google.gson.Gson

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun BottomNavGraph(navHostController: NavHostController, modifier: Modifier, isLoggedIn: Boolean) {
    val startDestination = if (isLoggedIn) BottomNavItem.Home.route else "start"

    NavHost(navHostController, startDestination = startDestination, modifier = modifier) {
        composable(BottomNavItem.Home.route) { HomeScreen(navHostController) }
        composable(BottomNavItem.Category.route) { CategoryScreen(navHostController) }
        composable(BottomNavItem.Diary.route) { DiaryScreen(navHostController) }
        composable(BottomNavItem.Bookmark.route) { BookmarkScreen(navHostController) }
        composable(BottomNavItem.My.route) { MyPageScreen(navHostController) }

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

        composable(
            route = "diaryWrite?diaryJson={diaryJson}&selectedDate={selectedDate}",
            arguments = listOf(
                navArgument("diaryJson") { defaultValue = "" },
                navArgument("selectedDate") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val diaryJson = backStackEntry.arguments?.getString("diaryJson")
            val diary: DiaryModel? = if (!diaryJson.isNullOrEmpty()) {
                Gson().fromJson(diaryJson, DiaryModel::class.java)
            } else null

            val selectedDate = backStackEntry.arguments?.getString("selectedDate") ?: ""

            DiaryWriteScreen(
                diary = diary,
                selectedDate = selectedDate,
                navHostController = navHostController
            )


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
