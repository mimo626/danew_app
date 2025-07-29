package com.example.danew.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "홈", Icons.Default.Home)
    object News : BottomNavItem("category", "카테고리", Icons.Default.Add)
    object Bookmark : BottomNavItem("diary", "기록", Icons.Default.Call)
    object History : BottomNavItem("bookmark", "북마크", Icons.Default.Done)
    object My : BottomNavItem("my", "MY", Icons.Default.Person)
}
