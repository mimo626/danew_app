package com.example.danew.core.componet

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.danew.core.navigation.BottomNavItem
import com.example.danew_app.core.theme.ColorsLight


@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.Diary,
        BottomNavItem.Bookmark,
        BottomNavItem.My
    )

    NavigationBar(
        containerColor = ColorsLight.whiteColor
    ){
        items.forEach { item ->
            NavigationBarItem(
                selected = navController.currentDestination?.route == item.route,
                onClick = { navController.navigate(item.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                icon = { Icon(item.icon(), contentDescription = item.title, modifier = Modifier.size(26.dp)) },
                label = { Text(item.title) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // 선택 시 배경색 제거
                )
            )
        }
    }
}
