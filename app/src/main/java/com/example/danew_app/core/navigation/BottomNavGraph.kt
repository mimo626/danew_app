package com.example.danew.core.navigation

import NewsDetailScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.danew.presentation.bookmark.BookmarkScreen
import com.example.danew.presentation.category.CategoryScreen
import com.example.danew.presentation.diary.DiaryScreen
import com.example.danew.presentation.home.HomeScreen
import com.example.danew.presentation.profile.MyPageScreen
import com.example.danew_app.domain.model.NewsModel

import com.google.gson.Gson

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = BottomNavItem.Home.route, modifier) {
        composable(BottomNavItem.Home.route) { HomeScreen(navController) }
        composable(BottomNavItem.Category.route) { CategoryScreen(navController) }
        composable(BottomNavItem.Diary.route) { DiaryScreen(navController) }
        composable(BottomNavItem.Bookmark.route) { BookmarkScreen(navController) }
        composable(BottomNavItem.My.route) { MyPageScreen(navController) }
        composable(
            "details/{newsId}",
        ) { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId")
            newsId?.let { NewsDetailScreen(it, navController) }

        }
    }
}
