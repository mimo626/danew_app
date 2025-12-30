package com.example.danew.presentation.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
    var isIdChecked by remember { mutableStateOf(false) }

    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    // 키보드 숨기기 제어용
    val keyboardController = LocalSoftwareKeyboardController.current

    val parentEntry = navHostController.getBackStackEntry("signupFlow")
    val viewModel: UserViewModel = hiltViewModel(parentEntry)

    val isUserIdAvailable = viewModel.isUserIdAvailable

    // 버튼 활성화 조건
    val isNextEnabled = id.isNotBlank() &&
            password.isNotBlank() &&
            confirmPassword.isNotBlank() &&
            password == confirmPassword &&
            isIdChecked &&
            isUserIdAvailable == true

    // 다음 단계로 이동하는 함수 (재사용을 위해 분리)
    val onNextClick = {
        if (isNextEnabled) {
            viewModel.updateUserId(id)
            viewModel.updatePassword(password)
            navHostController.navigate("signupAdd")
        } else {
            // 조건이 안 맞으면 키보드만 내림 (선택 사항)
            keyboardController?.hide()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MainTopAppBar(navController = navHostController, title = "", isBackIcon = true)
        },
        bottomBar = {
            BottomButton(
                text = "다음",
                isEnabled = isNextEnabled,
            ) {
                onNextClick()
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
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            // 1. 아이디 입력
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
                        isIdChecked = false
                    },
                    label = "아이디",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f),

                    // [핵심 2] 아이디 -> 비밀번호로 이동 설정
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocusRequester.requestFocus() }
                    )
                )
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    onClick = {
                        viewModel.checkUserId(id)
                        isIdChecked = true
                    },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("중복확인",
                        color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            when (viewModel.isUserIdAvailable) {
                true -> Text(
                    "사용 가능한 아이디입니다.",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                )
                false -> Text(
                    "이미 사용 중인 아이디입니다.",
                    color = MaterialTheme.colorScheme.onError,
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                )
                else -> {}
            }

            // 2. 비밀번호 입력
            CustomUnderlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "비밀번호",
                isPassword = true,

                // [핵심 3] 비밀번호 포커스 연결 및 -> 확인창 이동 설정
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .focusRequester(passwordFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { confirmPasswordFocusRequester.requestFocus() }
                )
            )

            // 3. 비밀번호 확인 입력
            CustomUnderlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "비밀번호 확인",
                isPassword = true,

                // [핵심 4] 확인창 포커스 연결 및 -> 완료 시 동작 설정
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .focusRequester(confirmPasswordFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done // 마지막이니까 Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onNextClick() } // 완료 시 다음 화면 이동 시도
                )
            )

            if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                Text(
                    "비밀번호가 일치하지 않습니다.",
                    color = MaterialTheme.colorScheme.onError,
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}