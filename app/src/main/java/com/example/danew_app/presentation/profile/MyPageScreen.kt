package com.example.danew.presentation.profile

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.danew_app.presentation.profile.MyPageMenuItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyPageScreen(navController: NavHostController,) {
    val userViewModel: UserViewModel = hiltViewModel()
    val user by userViewModel.getUserData.collectAsState()  // StateFlow 구독
    val logoutResult = userViewModel.logoutResult
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        userViewModel.getUser()   // 화면 진입 시 한 번 실행
    }

    LaunchedEffect(logoutResult) {
        if (logoutResult == "success"){
            navController.navigate("start")
        }
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
                    tint = ColorsLight.darkGrayColor,
                    modifier = Modifier
                        .size(56.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("${user.name.ifEmpty { "로딩 중" }}님",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,)
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${user.name}님의 관심사 키워드", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("수정", color = Color.Blue, fontSize = 12.sp,
                    modifier = Modifier.clickable{
                        navController.navigate("keywordUpdate")
                    })
            }
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
            ) {
                if(user.keywordList.isEmpty()){
                    item {
                        Text("관심사 키워드를 선택해 보세요.")
                    }
                }
                else{
                    items(user.keywordList) {
                        Box(
                            modifier = Modifier
                                .width(66.dp)
                                .padding(end = 12.dp)
                                .background(ColorsLight.lightGrayColor, shape = RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(it, maxLines = 1,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(vertical = 12.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 오늘 본 뉴스
            TodayNews(sectionTitle = "오늘 본 뉴스", navController)

            Spacer(modifier = Modifier.height(24.dp))
            // 설정 항목ㅌ 리스트
            MyPageMenuItem("공지사항") { }
            MyPageMenuItem("문의하기") { }
            MyPageMenuItem("자주 묻는 질문") { }
            MyPageMenuItem("오픈소스 라이센스") {
                val intent = Intent(context, OssLicensesMenuActivity::class.java)
                intent.putExtra("title", "오픈소스 라이센스") // 상단 타이틀 설정
                context.startActivity(intent)
            }
            MyPageMenuItem("버전 정보", trailing = { Text("1.0", color = ColorsLight.grayColor) }) { }
            MyPageMenuItem("로그아웃", textColor = ColorsLight.grayColor) {
                userViewModel.logout()
            }
        }
    }
}
