package com.example.danew.presentation.home
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.MainTopAppBar

@Composable
fun SettingScreen(navHostController: NavHostController,) {

    val settingList = listOf(
        "알림 설정", "개인정보 처리 방침", "서비스 이용약관", "로그아웃", "탈퇴하기"
    )
    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar( navController = navHostController,
                title = "설정",
                isBackIcon = true) },
    ) {
            padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(vertical = 16.dp)
        ) {
            items(settingList) { setting ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                ) {
                    Text(
                        setting,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = if(setting != "탈퇴하기") ColorsLight.blackColor else ColorsLight.grayColor,
                        modifier = Modifier.padding(horizontal = 20.dp).padding(vertical = 16.dp)
                    )
                    HorizontalDivider(color = ColorsLight.lightGrayColor)
                }
            }
        }
    }
}

