package com.example.danew.presentation.diary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.presentation.home.MainTopAppBar

@Composable
fun DiaryWriteScreen(date:String, navController: NavHostController,) {
    val selectedDate = date

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(navController = navController, title = "기록하기", icon = Icons.Default.MoreVert,
                isHome = false, isBackIcon = true)
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(24.dp))
            // 날짜
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(selectedDate, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box (modifier = Modifier
                .fillMaxSize()
                .clickable{
                }
            ) {
                Text(
                    "뉴스를 통해 어떤 것을 느끼셨나요?",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }

        }
    }
}
