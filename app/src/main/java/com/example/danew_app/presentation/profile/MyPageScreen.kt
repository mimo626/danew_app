package com.example.danew.presentation.profile

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.profile.TodayNews
import com.example.danew_app.presentation.viewmodel.UserViewModel
import androidx.compose.runtime.collectAsState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyPageScreen(navController: NavHostController,) {
    val userViewModel: UserViewModel = hiltViewModel()
    val user by userViewModel.getUserData.collectAsState()  // StateFlow 구독

    LaunchedEffect(Unit) {
        userViewModel.getUser()   // 화면 진입 시 한 번 실행
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                navController = navController,
                title = "마이페이지",
                icon = Icons.Default.Settings,
                isHome = false)
            {
                navController.navigate("settings")
            }
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
                    Text("${user.name.ifEmpty { "로딩 중" }}님", fontWeight = FontWeight.Bold)
                }

                IconButton(onClick = {
                    navController.navigate("profileEdit")
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "프로필 수정",
                       tint = ColorsLight.grayColor)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(thickness = 6.dp, color = ColorsLight.lightGrayColor)

            Spacer(modifier = Modifier.height(16.dp))

            // 오늘 본 뉴스
            TodayNews(sectionTitle = "오늘 본 뉴스", navController)

            Spacer(modifier = Modifier.height(24.dp))
            // 설정 항목ㅌ 리스트
            MyPageMenuItem("공지사항") { }
            MyPageMenuItem("문의하기") { }
            MyPageMenuItem("자주 묻는 질문") { }
            MyPageMenuItem("오픈소스 라이센스") { }
            MyPageMenuItem("버전 정보", trailing = { Text("1.0", color = ColorsLight.grayColor) }) { }
            MyPageMenuItem("로그아웃", textColor = ColorsLight.grayColor) {
                userViewModel.logout()
                navController.navigate("/start")
            }
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
        HorizontalDivider(color = ColorsLight.lightGrayColor)
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
