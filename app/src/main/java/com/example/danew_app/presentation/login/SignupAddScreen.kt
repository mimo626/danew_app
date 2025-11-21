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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.CustomLinearProgressIndicator
import com.example.danew_app.core.widget.CustomRadioButton
import com.example.danew_app.core.widget.CustomUnderlinedTextField
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.UserViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun SignupAddScreen(navHostController: NavHostController, viewModel: UserViewModel) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("여성") }
    val parentEntry = navHostController.getBackStackEntry("signupFlow")
    val viewModel: UserViewModel = hiltViewModel(parentEntry)

    val ageFocusRequester = remember { FocusRequester() }

    // 키보드 숨기기 제어용
    val keyboardController = LocalSoftwareKeyboardController.current

    // 버튼 활성화 조건
    val isNextEnabled = name.isNotBlank() &&
            age.isNotBlank() &&
            gender.isNotBlank()

    val onNextClick = {
        if (isNextEnabled) {
            viewModel.updateName(name)
            viewModel.updateAge(age.toInt())
            viewModel.updateGender(gender)

            navHostController.navigate("keyword")
        } else {
            // 조건이 안 맞으면 키보드만 내림 (선택 사항)
            keyboardController?.hide()
        }
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(navController = navHostController, title = "", isBackIcon = true)
        },
        bottomBar = {
            // 완료 버튼
            BottomButton(text = "다음",
                isEnabled = isNextEnabled
            ) {
                onNextClick()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            CustomLinearProgressIndicator(progress = 2/4f)

            Spacer(Modifier.height(32.dp))

            Text("추가 정보 입력", fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp),)

            Spacer(modifier = Modifier.weight(1f))

            // 이름 입력
            CustomUnderlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = "이름",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { ageFocusRequester.requestFocus() }
                )
            )

            // 생년월일 입력
            CustomUnderlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = "나이",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                    .focusRequester(ageFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {  }
                )
            )

            // 성별
            Column(
                modifier = Modifier.fillMaxWidth().
                padding(horizontal = 38.dp),
            ) {
                Text(
                    text = "성별",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = ColorsLight.grayColor
                    ),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomRadioButton(
                        selected = gender == "남성",
                        text = "남성"
                    ) {
                        gender = "남성"
                    }
                }
                Spacer(Modifier.width(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomRadioButton(
                        selected = gender == "여성",
                        text = "여성"
                    ) {
                        gender = "여성"
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
