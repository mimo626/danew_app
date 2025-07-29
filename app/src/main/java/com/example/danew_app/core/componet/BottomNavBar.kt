package com.example.danew.core.componet

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.danew.core.navigation.BottomNavItem

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.News,
        BottomNavItem.Bookmark,
        BottomNavItem.History,
        BottomNavItem.My
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = navController.currentDestination?.route == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}
