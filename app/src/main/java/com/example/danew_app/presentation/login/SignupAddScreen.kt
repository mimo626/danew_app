package com.example.danew.presentation.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.CustomLinearProgressIndicator
import com.example.danew_app.core.widget.CustomRadioButton
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.SignupViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun SignupAddScreen(navHostController: NavHostController, viewModel: SignupViewModel) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("여성") }
    val parentEntry = navHostController.getBackStackEntry("signupFlow")
    val viewModel: SignupViewModel = hiltViewModel(parentEntry)

    // 버튼 활성화 조건
    val isNextEnabled = name.isNotBlank() &&
            age.isNotBlank() &&
            gender.isNotBlank()

    Scaffold(
        topBar = {
            MainTopAppBar(navController = navHostController, title = "", isBackIcon = true)
        },
        bottomBar = {
            // 완료 버튼
            BottomButton(text = "다음",
                isEnabled = isNextEnabled
            ) {
                viewModel.updateName(name)
                viewModel.updateAge(age.toInt())
                viewModel.updateGender(gender)

                navHostController.navigate("keyword")
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
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            // 이름 입력
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("이름") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            )

            Spacer(Modifier.height(16.dp))

            // 생년월일 입력
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("나이") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            )

            Spacer(Modifier.height(16.dp))

            // 성별
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("성별", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.width(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
