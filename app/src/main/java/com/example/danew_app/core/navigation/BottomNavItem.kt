// package com.example.danew.core.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.danew_app.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    @DrawableRes val unselectedIconResId: Int, // 1. 선택 안 된 아이콘
    @DrawableRes val selectedIconResId: Int   // 2. 선택된 아이콘
) {
    // 3. 여기에 실제 "채워진" 아이콘 리소스 ID를 넣으세요.
    // (예: R.drawable.home_filled)
    object Home : BottomNavItem(
        "home",
        "홈",
        R.drawable.home,
        R.drawable.home_fill
    )
    object Category : BottomNavItem(
        "category",
        "카테고리",
        R.drawable.category,
        R.drawable.category_fill
    )
    object Diary : BottomNavItem(
        "diary",
        "기록",
        R.drawable.diary,
        R.drawable.diary_fill
    )
    object Bookmark : BottomNavItem(
        "bookmark",
        "북마크",
        R.drawable.bookmark,
        R.drawable.bookmark_fill
    )
    object My : BottomNavItem(
        "my",
        "MY",
        R.drawable.profile,
        R.drawable.profile_fill
    )

    /**
     * @param isSelected 현재 선택되었는지 여부
     * @return 선택 상태에 맞는 Painter 리소스
     */
    @Composable
    fun icon(isSelected: Boolean): Painter {
        // 4. 선택 여부에 따라 올바른 아이콘 리소스를 반환
        val iconRes = if (isSelected) selectedIconResId else unselectedIconResId
        return painterResource(id = iconRes)
    }
}