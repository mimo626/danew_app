package com.example.danew.core.navigation

import MainScreen
import NewsDetailScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.danew.presentation.bookmark.BookmarkScreen
import com.example.danew.presentation.category.CategoryScreen
import com.example.danew.presentation.diary.DiaryScreen
import com.example.danew.presentation.diary.DiaryWriteScreen
import com.example.danew.presentation.home.HomeScreen
import com.example.danew.presentation.profile.MyPageScreen

@Composable
fun BottomNavGraph(navHostController: NavHostController, modifier: Modifier) {

    NavHost(navHostController, startDestination = BottomNavItem.Home.route, modifier = modifier) {
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
        composable("details/{newsId}") { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId")
            newsId?.let { NewsDetailScreen(it, navHostController) }
        }

        composable("diary/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            date?.let { DiaryWriteScreen(it, navHostController) }
        }
    }
}
