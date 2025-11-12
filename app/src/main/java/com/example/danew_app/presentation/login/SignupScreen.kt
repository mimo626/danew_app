package com.example.danew.presentation.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.CustomLinearProgressIndicator
import com.example.danew_app.core.widget.CustomUnderlinedTextField
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.UserViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun SignupScreen(navHostController: NavHostController, viewModel: UserViewModel) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isIdChecked by remember { mutableStateOf(false) } // 중복 체크 버튼 눌렀는지 여부

    val parentEntry = navHostController.getBackStackEntry("signupFlow")
    val viewModel: UserViewModel = hiltViewModel(parentEntry)

    // ViewModel 상태 관찰
    val isUserIdAvailable = viewModel.isUserIdAvailable

    // 버튼 활성화 조건
    val isNextEnabled = id.isNotBlank() &&
            password.isNotBlank() &&
            confirmPassword.isNotBlank() &&
            password == confirmPassword &&
            isIdChecked &&
            isUserIdAvailable == true

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(navController = navHostController, title = "", isBackIcon = true)
        },
        bottomBar = {
            BottomButton(
                text = "다음",
                isEnabled = isNextEnabled
            ) {
                viewModel.updateUserId(id)
                viewModel.updatePassword(password)
                navHostController.navigate("signupAdd")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            CustomLinearProgressIndicator(1 / 4f)
            Spacer(Modifier.height(32.dp))

            Text(
                "회원 정보 입력",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            // 아이디 입력
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                CustomUnderlinedTextField(
                    value = id,
                    onValueChange = {
                        id = it
                        isIdChecked = false // 아이디 변경되면 다시 체크해야 함
                    },
                    label = "아이디",
                    modifier = Modifier.padding(end = 8.dp).weight(1f)
                )
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = ColorsLight.darkGrayColor),
                    onClick = {
                    viewModel.checkUserId(id)
                    isIdChecked = true
                    },
                    modifier = Modifier.height(56.dp) // TextField와 같은 높이
                ) {
                    Text("중복확인")
                }
            }

            // 중복 확인 결과 표시
            when (viewModel.isUserIdAvailable) {
                true -> Text(
                    "사용 가능한 아이디입니다.",
                    color = ColorsLight.primaryColor,
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                )
                false -> Text(
                    "이미 사용 중인 아이디입니다.",
                    color = ColorsLight.redColor,
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                )
                else -> {}
            }

            // 비밀번호 입력
            CustomUnderlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "비밀번호",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                isPassword = true,
            )

            // 비밀번호 확인 입력
            CustomUnderlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "비밀번호 확인",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                isPassword = true,
            )

            // 비밀번호 불일치 시 안내
            if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                Text(
                    "비밀번호가 일치하지 않습니다.",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
