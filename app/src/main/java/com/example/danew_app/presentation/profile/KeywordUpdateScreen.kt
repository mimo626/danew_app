package com.example.danew_app.presentation.profile

import android.annotation.SuppressLint
import com.example.danew_app.presentation.viewmodel.UserViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.CustomSnackbarHost
import com.example.danew_app.core.widget.CustomUnderlinedTextField
import com.example.danew_app.core.widget.KeywordGrid
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.core.widget.showImmediateSnackbar

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun KeywordUpdateScreen(navHostController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val userViewModel: UserViewModel = hiltViewModel()
    val user by userViewModel.getUserData.collectAsState()
    val userKeywordList = user.keywordList
    var createdKeyword by remember { mutableStateOf("") }

    val allKeywords = remember {
        mutableStateListOf(
            "정치", "엔터", "경제",
            "취업", "문화", "IT",
            "세계", "AI", "사회",
            "스포츠", "금융", "주식"
        )
    }

    // 선택된 키워드 상태
    val selectedKeywords = remember { mutableStateListOf<String>() }

    // 버튼 활성화 조건
    val isNextEnabled = selectedKeywords.isNotEmpty()

    LaunchedEffect(Unit) {
        userViewModel.getUser()   // 화면 진입 시 한 번 실행
    }

    LaunchedEffect(userKeywordList) {
        if (userKeywordList.isNotEmpty()) {
            userKeywordList.forEach {
                if (!allKeywords.contains(it)) {
                    allKeywords.add(0, it)
                }
            }
            selectedKeywords.clear()
            selectedKeywords.addAll(userKeywordList)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) },
        topBar = {
            MainTopAppBar(navController = navHostController,
                title = "키워드 수정",
                isBackIcon = true
            )
        },
        bottomBar = {
            // 완료 버튼
            BottomButton(text = "완료",
                isEnabled = isNextEnabled
            ) {
                userViewModel.updateKeyword(selectedKeywords)
                navHostController.popBackStack()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(32.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                CustomUnderlinedTextField(
                    value = createdKeyword,
                    onValueChange = {
                        createdKeyword = it
                    },
                    label = "키워드",
                    modifier = Modifier.padding(end = 8.dp).weight(1f)
                )
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    onClick = {
                        if (createdKeyword.isNotBlank() && !allKeywords.contains(createdKeyword)) {
                            if(selectedKeywords.size < 5){
                                allKeywords.add(0, createdKeyword)
                                selectedKeywords.add(createdKeyword)
                                createdKeyword = ""
                            }
                            else {
                                snackbarHostState.showImmediateSnackbar(
                                    scope = scope,
                                    message = "최대 5개까지 선택할 수 있습니다."
                                )
                            }
                        }
                    },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("생성", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
            Text(
                "최대 5개까지 선택할 수 있습니다.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            )
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
        }
    }
}