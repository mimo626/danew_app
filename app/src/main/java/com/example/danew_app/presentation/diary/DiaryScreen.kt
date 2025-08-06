package com.example.danew.presentation.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.danew_app.presentation.home.MainTopAppBar

@Composable
fun DiaryScreen() {
    var selectedTab by remember { mutableStateOf("일간") }

    Scaffold(
        topBar = {
            MainTopAppBar(title = "기록", icon = Icons.Default.MoreVert,
               isHome = false)
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 일간/월간 토글
            Row(
                modifier = Modifier
                    .background(Color(0xFFF0F0F0), RoundedCornerShape(20.dp))
                    .padding(4.dp)
            ) {
                listOf("일간", "월간").forEach { tab ->
                    val isSelected = tab == selectedTab
                    Box(
                        modifier = Modifier
                            .background(if (isSelected) Color(0xFF2BAF53) else Color.Transparent,
                                RoundedCornerShape(20.dp))
                            .clickable { selectedTab = tab }
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(
                            tab,
                            color = if (isSelected) Color.White else Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 날짜 및 선
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("2025.07.20", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "뉴스를 통해 어떤 것을 느끼셨나요?",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}
