package com.example.danew.core.navigation

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
fun BottomNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = BottomNavItem.Home.route, modifier) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.News.route) { CategoryScreen() }
        composable(BottomNavItem.Bookmark.route) { DiaryScreen() }
        composable(BottomNavItem.History.route) { BookmarkScreen() }
        composable(BottomNavItem.My.route) { MyPageScreen() }
    }
}
