package com.example.danew_app.presentation.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.core.widget.CustomLinearProgressIndicator

@Composable
fun KeywordScreen(navHostController: NavHostController) {
    val allKeywords:List<String> = listOf(
        "정치", "엔터", "경제",
        "취업", "문화", "IT",
        "세계", "AI", "사회",
        "스포츠", "금융", "주식"
    )

    // 선택된 키워드 상태
    val selectedKeywords = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            MainTopAppBar(navController = navHostController, title = "", isBackIcon = true)
        },
        bottomBar = {
            // 완료 버튼
            BottomButton(text = "다음") {
                Log.d("KeywordSelect", "${selectedKeywords}")
                navHostController.navigate("signupFinish")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            CustomLinearProgressIndicator(progress = 3/4f)
            Spacer(Modifier.height(32.dp))

            Text("관심 키워드 선택", fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Text(
                "최대 5개까지 선택할 수 있습니다.",
                fontSize = 14.sp,
                color = ColorsLight.grayColor,
                modifier = Modifier.padding(horizontal = 20.dp),
                )
            Spacer(modifier = Modifier.weight(1f))

            // 키워드 그리드
            LazyVerticalGrid(

                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                content = {
                    items(allKeywords) { keyword ->
                        val isSelected = selectedKeywords.contains(keyword)

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (isSelected) ColorsLight.darkGrayColor else ColorsLight.lightGrayColor
                                )
                                .clickable {
                                    if (isSelected) {
                                        selectedKeywords.remove(keyword) // 이미 있으면 제거
                                    } else {
                                        if (selectedKeywords.size < 5) {
                                            selectedKeywords.add(keyword) // 5개 미만일 때만 추가
                                        }
                                    }
                                }
                                .weight(1f) // 가로를 3등분
                                .aspectRatio(1f) // 가로 = 세로 → 정사각형
                                .fillMaxHeight()                            ,
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = keyword,
                                color = if (isSelected) Color.White else Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}