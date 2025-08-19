package com.example.danew_app.core.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight

// 앱바
@Composable
fun MainTopAppBar(
    navController: NavHostController,
    title: String,
    icon: ImageVector? = null,
    isHome: Boolean = false,
    isBackIcon: Boolean = false,
    onClick: () -> Unit = {  },
) {
    Surface(color = ColorsLight.whiteColor) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 12.dp)
        ) {
            // 왼쪽 끝 - 뒤로가기 버튼
            if (isBackIcon) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로가기"
                    )
                }
            }

            // 가운데 - 타이틀
            Text(
                text = title,
                fontWeight = if (isHome) FontWeight.Bold else FontWeight.SemiBold,
                color = if (isHome) ColorsLight.primaryColor else ColorsLight.blackColor,
                fontSize = if (isHome) 22.sp else 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.Center)
            )

            // 오른쪽 끝 - 아이콘
            if (icon != null) {
                IconButton(
                    onClick = onClick,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    }
}
