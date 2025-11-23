package com.example.danew.core.navigation



import SearchResultScreen
import SearchScreen
import android.annotation.SuppressLint
import android.os.Build
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
import com.example.danew.presentation.profile.AnnounceDetailScreen
import com.example.danew.presentation.profile.AnnounceScreen
import com.example.danew.presentation.profile.MyPageScreen
import com.example.danew.presentation.profile.ProfileEditScreen
import com.example.danew_app.data.entity.NewsDetailType
import com.example.danew_app.domain.model.DiaryModel
import com.example.danew_app.presentation.bookmark.NoScrollNewsDetailScreen
import com.example.danew_app.presentation.login.KeywordScreen
import com.example.danew_app.presentation.profile.KeywordUpdateScreen
import com.example.danew_app.presentation.profile.QuestionScreen
import com.example.danew_app.presentation.profile.TodayNewsScreen
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.presentation.viewmodel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun BottomNavGraph(navHostController: NavHostController, modifier: Modifier, isLoggedIn: Boolean) {
    val startDestination = if (isLoggedIn) BottomNavItem.Home.route else "start"
    val sharedNewsViewModel: NewsViewModel = hiltViewModel()
    val sharedCategoryNewsViewModel: NewsViewModel = hiltViewModel()

    NavHost(navHostController, startDestination = startDestination, modifier = modifier) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(navHostController, newsViewModel = sharedNewsViewModel) }
        composable(BottomNavItem.Category.route) { CategoryScreen(navHostController,
            newsViewModel = sharedCategoryNewsViewModel) }
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
            route = "details/{listType}/{newsId}",
            arguments = listOf(
                navArgument("listType") { type = NavType.StringType },
                navArgument("newsId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // 1. 문자열로 받기 (오타 수정: ge -> getString)
            val typeString = backStackEntry.arguments?.getString("listType")

            // 2. 문자열을 Enum으로 변환 (핵심!)
            val newsType: NewsDetailType = try {
                if (typeString != null) NewsDetailType.valueOf(typeString)
                else NewsDetailType.SEARCH
            } catch (e: IllegalArgumentException) {
                NewsDetailType.SEARCH // 혹시 모를 변환 에러 방지
            }

            val newsId = backStackEntry.arguments?.getString("newsId")

            // 3. 화면에 Enum 타입으로 전달
            if(newsId != null){
                if (newsType == NewsDetailType.SEARCH ||
                    newsType==NewsDetailType.TODAY ||
                    newsType==NewsDetailType.BOOKMARK) {
                    NoScrollNewsDetailScreen(
                        newsId = newsId,
                        listType = newsType, // 이제 String이 아니라 Enum이 들어갑니다
                        navHostController = navHostController // (변수명 navController 확인 필요)
                    )
                }
                if (newsType == NewsDetailType.HOME){
                    NewsDetailMainScreen(
                        initialNewsId = newsId,
                        listType = newsType, // listType 전달
                        navHostController = navHostController,
                        newsViewModel = sharedNewsViewModel
                    )
                }
                if(newsType == NewsDetailType.CATEGORY){
                    NewsDetailMainScreen(
                        initialNewsId = newsId,
                        listType = newsType, // listType 전달
                        navHostController = navHostController,
                        newsViewModel = sharedCategoryNewsViewModel
                    )
                }
            }
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

        composable("announce") {
            AnnounceScreen(navController = navHostController)
        }

        composable("announce/{announceId}") { backStackEntry ->
            val announceId = backStackEntry.arguments?.getString("announceId")
            announceId?.let { AnnounceDetailScreen(announceId, navHostController) }
        }

        composable("question") {
            QuestionScreen(navController = navHostController)
        }
    }
}
