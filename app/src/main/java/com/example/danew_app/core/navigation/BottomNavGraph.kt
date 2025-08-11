package com.example.danew.core.navigation

import MainScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.danew.presentation.bookmark.BookmarkScreen
import com.example.danew.presentation.category.CategoryScreen
import com.example.danew.presentation.diary.DiaryScreen
import com.example.danew.presentation.home.HomeScreen
import com.example.danew.presentation.profile.MyPageScreen

@Composable
fun BottomNavGraph(bottomNavController: NavHostController, rootNavController: NavHostController,
                   modifier: Modifier) {
    // modifier를 기본값으로 주어 innerPadding 중첩 방지
    NavHost(bottomNavController, startDestination = BottomNavItem.Home.route, modifier = Modifier) {
        composable(BottomNavItem.Home.route) { HomeScreen(rootNavController) }
        composable(BottomNavItem.Category.route) { CategoryScreen(rootNavController) }
        composable(BottomNavItem.Diary.route) { DiaryScreen(rootNavController) }
        composable(BottomNavItem.Bookmark.route) { BookmarkScreen(rootNavController) }
        composable(BottomNavItem.My.route) { MyPageScreen(rootNavController) }
    }
}
