package com.example.danew.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.danew_app.core.gloabals.Globals
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.presentation.home.MainTopAppBar
import com.example.danew_app.presentation.profile.TodayNews

@Composable
fun MyPageScreen(navController: NavHostController,) {
    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                title = "마이페이지",
                icon = Icons.Default.Settings,
                isHome = false)
        },
        bottomBar = {
            // 하단 네비게이션은 생략 가능 (이미 구현돼 있다면 재사용)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // 유저 정보
            Row(modifier = Modifier.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "프로필",
                    tint = ColorsLight.primaryColor,
                    modifier = Modifier
                        .size(56.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("email@email.com", fontWeight = FontWeight.Bold)
                    Text("이름", color = Color.Gray)
                }

                IconButton(onClick = { /* 프로필 수정 */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "프로필 수정",
                       tint = ColorsLight.grayColor)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Divider(thickness = 6.dp, color = ColorsLight.lightGrayColor)

            Spacer(modifier = Modifier.height(16.dp))

            // 오늘 본 뉴스
            TodayNews(sectionTitle = "오늘 본 뉴스", newsList = Globals.dummyNewsList, navController)

            Spacer(modifier = Modifier.height(24.dp))
            // 설정 항목 리스트
            MyPageMenuItem("공지사항") { }
            MyPageMenuItem("문의하기") { }
            MyPageMenuItem("자주 묻는 질문") { }
            MyPageMenuItem("오픈소스 라이센스") { }
            MyPageMenuItem("버전 정보", trailing = { Text("1.0", color = ColorsLight.grayColor) }) { }
            MyPageMenuItem("로그아웃", textColor = ColorsLight.grayColor) { /* 로그아웃 로직 */ }
        }
    }
}

@Composable
fun MyPageMenuItem(
    title: String,
    textColor: Color = Color.Black,
    trailing: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 20.dp)
    ) {
        Divider(color = ColorsLight.lightGrayColor)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = textColor)
            trailing?.invoke()
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}
