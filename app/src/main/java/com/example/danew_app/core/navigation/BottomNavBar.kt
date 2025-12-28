package com.example.danew.core.componet

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.DanewColors


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
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        items.forEach { item ->
            // 1. 현재 아이템이 선택되었는지 확인
            val selected = navController.currentDestination?.route == item.route

            NavigationBarItem(
                selected = selected, // 2. 'selected' 상태 적용
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        // 3. 'selected' 값을 전달하여 상태에 맞는 아이콘 표시
                        painter = item.icon(isSelected = selected),
                        contentDescription = item.title,
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = { Text(item.title) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent, // 선택 시 배경색 제거

                    // 4. 선택 상태에 따라 아이콘 및 텍스트 색상 변경
                    selectedIconColor = MaterialTheme.colorScheme.primary, // 선택된 아이콘 색
                    unselectedIconColor = MaterialTheme.colorScheme.tertiary, // 선택 안 된 아이콘 색
                    selectedTextColor = MaterialTheme.colorScheme.primary, // 선택된 텍스트 색
                    unselectedTextColor = MaterialTheme.colorScheme.tertiary,// 선택 안 된 텍스트 색
                )
            )
        }
    }
}