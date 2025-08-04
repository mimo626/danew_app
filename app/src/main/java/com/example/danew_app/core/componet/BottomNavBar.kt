package com.example.danew.core.componet

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBarDefaults.containerColor
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.danew.core.navigation.BottomNavItem


@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.Diary,
        BottomNavItem.Bookmark,
        BottomNavItem.My
    )

    NavigationBar{
        items.forEach { item ->
            NavigationBarItem(
                selected = navController.currentDestination?.route == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon(), contentDescription = item.title, modifier = Modifier.size(26.dp)) },
                label = { Text(item.title) }
            )
        }
    }
}
