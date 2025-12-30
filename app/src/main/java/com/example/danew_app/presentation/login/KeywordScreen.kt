package com.example.danew_app.presentation.login

import android.annotation.SuppressLint
import com.example.danew_app.presentation.viewmodel.UserViewModel
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.DanewColors
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.core.widget.CustomLinearProgressIndicator
import com.example.danew_app.core.widget.CustomSnackbarHost
import com.example.danew_app.core.widget.KeywordGrid
import com.example.danew_app.core.widget.showImmediateSnackbar

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun KeywordScreen(navHostController: NavHostController, viewModel: UserViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val allKeywords:List<String> = listOf(
        "정치", "엔터", "경제",
        "취업", "문화", "IT",
        "세계", "AI", "사회",
        "스포츠", "금융", "주식"
    )

    // 선택된 키워드 상태
    val selectedKeywords = remember { mutableStateListOf<String>() }

    val parentEntry = navHostController.getBackStackEntry("signupFlow")
    val viewModel: UserViewModel = hiltViewModel(parentEntry)

    // 버튼 활성화 조건
    val isNextEnabled = selectedKeywords.isNotEmpty()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) },
        topBar = {
            MainTopAppBar(navController = navHostController, title = "", isBackIcon = true)
        },
        bottomBar = {
            // 완료 버튼
            BottomButton(text = "다음",
                isEnabled = isNextEnabled
                ) {
                viewModel.updateKeywordList(selectedKeywords)
                viewModel.completeSignup()
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

            Text("관심 키워드 선택",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Text(
                "최대 5개까지 선택할 수 있습니다.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 20.dp),
                )
            Spacer(modifier = Modifier.weight(1f))

            // 키워드 그리드
            KeywordGrid(
                allKeywords = allKeywords,
                selectedKeywords = selectedKeywords, // remember { mutableStateListOf(...) } 전달
                maxSelection = 5,
                onMaxReached = {
                    snackbarHostState.showImmediateSnackbar(
                        scope = scope,
                        message = "최대 5개까지 선택할 수 있습니다."
                    )
                }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}