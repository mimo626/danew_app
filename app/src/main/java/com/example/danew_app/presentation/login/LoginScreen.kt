package com.example.danew.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.SignupViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.danew_app.core.theme.ColorsLight


@Composable
fun LoginScreen(navHostController: NavHostController, viewModel: SignupViewModel= hiltViewModel()) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginResult = viewModel.loginResult
    val errorMessage = viewModel.errorMessage

    // 로그인 성공 시 화면 이동
    LaunchedEffect(loginResult) {
        if (loginResult == "success") {
            navHostController.navigate("home")
        }
    }

    Scaffold(
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {

            Spacer(modifier = Modifier.weight(1f))

            // 아이디 입력
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("아이디") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            )

            Spacer(Modifier.height(16.dp))

            // 비밀번호 입력
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            )

            // 로그인 결과 메시지 표시
            errorMessage?.let {
                Text(
                    text = it,
                    color = ColorsLight.redColor,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
