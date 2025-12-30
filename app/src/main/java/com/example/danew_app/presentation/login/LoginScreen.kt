package com.example.danew.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.DanewColors
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.CustomUnderlinedTextField
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.UserViewModel

@Composable
fun LoginScreen(navHostController: NavHostController, viewModel: UserViewModel = hiltViewModel()) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // [핵심 1] 포커스 제어를 위한 Requester 생성
    val passwordFocusRequester = remember { FocusRequester() }

    val loginResult = viewModel.loginResult
    val errorMessage = viewModel.errorMessage

    // 로그인 성공 시 화면 이동
    LaunchedEffect(loginResult) {
        if (loginResult == "success") {
            navHostController.navigate("home")
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MainTopAppBar(navController = navHostController, title = "로그인", isBackIcon = true)
        },
        bottomBar = {
            // 완료 버튼
            BottomButton(
                text = "로그인하기",
                onClick = { viewModel.login(id, password) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {

            Spacer(modifier = Modifier.weight(1f))

            // 아이디 입력
            CustomUnderlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = "아이디",
                // [핵심 2] 키보드 옵션: '다음' 버튼 표시
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                // [핵심 3] '다음' 클릭 시 비밀번호 창으로 포커스 이동
                keyboardActions = KeyboardActions(
                    onNext = { passwordFocusRequester.requestFocus() }
                )
            )

            // 비밀번호 입력
            CustomUnderlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "비밀번호",
                isPassword = true,
                // [핵심 4] Modifier에 FocusRequester 연결
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .focusRequester(passwordFocusRequester),
                // [핵심 5] 키보드 옵션: '완료' 버튼 표시
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                // [핵심 6] '완료' 클릭 시 로그인 함수 실행
                keyboardActions = KeyboardActions(
                    onDone = { viewModel.login(id, password) }
                )
            )

            // 로그인 결과 메시지 표시
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onError,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}