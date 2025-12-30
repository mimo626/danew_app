package com.example.danew.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.CustomLinearProgressIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.UserViewModel

@Composable
fun SignupFinishScreen(navHostController: NavHostController, viewModel: UserViewModel) {

    val signUpResult = viewModel.signUpResult
    val errorMessage = viewModel.errorMessage
    val iconColor = if(signUpResult == "success") MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
    val icon = if(signUpResult == "success") Icons.Default.Done else Icons.Default.Close
    var infoText by remember { mutableStateOf("") }

    LaunchedEffect(signUpResult, errorMessage) {
        infoText = if (signUpResult == "success") "회원가입 완료!"
        else "회원가입 실패: ${errorMessage ?: ""}"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MainTopAppBar(navController = navHostController, title = "", isBackIcon = true)
        },
        bottomBar = {
            // 완료 버튼
            BottomButton(text = "로그인하러가기") {
                navHostController.navigate("login")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CustomLinearProgressIndicator(progress = 1f)

            Spacer(Modifier.weight(1f))

            Text(infoText, fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(Modifier.weight(1f))
        }
    }
}
