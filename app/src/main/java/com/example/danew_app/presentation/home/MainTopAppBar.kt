package com.example.danew_app.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.danew_app.core.theme.ColorsLight

// 앱바
@Composable
fun MainTopAppBar(title:String, icon: ImageVector, isHome:Boolean) {
    Surface(
        color = ColorsLight.whiteColor,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // 원하는 높이 설정
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (isHome){
                    Text(
                        title,
                        fontWeight = FontWeight.Bold,
                        color = ColorsLight.primaryColor,
                        fontSize = 22.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                else {
                    Text(
                        title,
                        color = ColorsLight.blackColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    }
}