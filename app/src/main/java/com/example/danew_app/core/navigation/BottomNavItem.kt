package com.example.danew.core.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.danew_app.R

sealed class BottomNavItem(val route: String, val title: String,
                           @DrawableRes val iconResId: Int)
{
    object Home : BottomNavItem("home", "홈", R.drawable.home)
    object Category : BottomNavItem("category", "카테고리", R.drawable.category)
    object Diary : BottomNavItem("diary", "기록", R.drawable.diary)
    object Bookmark : BottomNavItem("bookmark", "북마크", R.drawable.bookmark)
    object My : BottomNavItem("my", "MY", R.drawable.mypage)

    @Composable
    fun icon(): Painter {
        return painterResource(id = iconResId)
    }
}
