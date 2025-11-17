package com.example.danew.core.navigation



import SearchResultScreen
import SearchScreen
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
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
import com.example.danew.presentation.home.NewsDetailMainScreen
import com.example.danew.presentation.home.SettingScreen
import com.example.danew.presentation.home.StartScreen
import com.example.danew.presentation.login.LoginScreen
import com.example.danew.presentation.login.SignupAddScreen
import com.example.danew.presentation.login.SignupFinishScreen
import com.example.danew.presentation.login.SignupScreen
import com.example.danew.presentation.profile.MyPageScreen
import com.example.danew.presentation.profile.ProfileEditScreen
import com.example.danew_app.domain.model.DiaryModel
import com.example.danew_app.presentation.bookmark.NoScrollNewsDetailScreen
import com.example.danew_app.presentation.login.KeywordScreen
import com.example.danew_app.presentation.profile.KeywordUpdateScreen
import com.example.danew_app.presentation.profile.TodayNewsScreen
import com.example.danew_app.presentation.viewmodel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
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
                val viewModel: UserViewModel =
                    hiltViewModel(navHostController.getBackStackEntry("signupFlow"))
                SignupScreen(navHostController, viewModel)
            }
            composable("signupAdd") { backStackEntry ->
                val viewModel: UserViewModel =
                    hiltViewModel(navHostController.getBackStackEntry("signupFlow"))
                SignupAddScreen(navHostController, viewModel)
            }
            composable("keyword") { backStackEntry ->
                val viewModel: UserViewModel =
                    hiltViewModel(navHostController.getBackStackEntry("signupFlow"))
                KeywordScreen(navHostController, viewModel)
            }
            composable("signupFinish") { backStackEntry ->
                val viewModel: UserViewModel =
                    hiltViewModel(navHostController.getBackStackEntry("signupFlow"))
                SignupFinishScreen(navHostController, viewModel)
            }
        }

        composable(
            // 1. 새로운 경로 적용
            route = "detail/{listType}/{index}?category={categoryName}",
            arguments = listOf(
                navArgument("listType") { type = NavType.StringType },
                navArgument("index") { type = NavType.IntType },
                navArgument("categoryName") { // 선택적 인자
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            // 2. 인자들 꺼내기
            val listType = backStackEntry.arguments?.getString("listType") ?: "home"
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            val categoryName = backStackEntry.arguments?.getString("categoryName")

            Log.d("News 상세: ", "${index}")

            // 3. NewsDetailScreen에 전달
            NewsDetailMainScreen(
                initialIndex = index,
                listType = listType, // listType 전달
                categoryName = categoryName, // categoryName 전달
                navHostController = navHostController
            )
        }
        composable("details/noScroll/{newsId}") { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId")
            newsId?.let { NoScrollNewsDetailScreen(it, navHostController) }
        }

        composable(
            route = "diaryWrite?selectedDate={selectedDate}",
            arguments = listOf(
                navArgument("selectedDate") { defaultValue = "" }
            )
        ) { backStackEntry ->
            // SavedStateHandle 에서 DiaryModel 가져오기
            val diary = navHostController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<DiaryModel>("diary")

            // 쿼리로 넘어온 selectedDate 가져오기
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

        composable("todayNews") {
            TodayNewsScreen(navHostController = navHostController)
        }

        composable("keywordUpdate") { backStackEntry ->
            KeywordUpdateScreen(navHostController)
        }
    }
}
