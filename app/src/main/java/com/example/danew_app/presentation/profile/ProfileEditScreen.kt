package com.example.danew.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.CustomRadioButton
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.UserViewModel

@Composable
fun ProfileEditScreen(navHostController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val user by userViewModel.getUserData.collectAsState()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        userViewModel.getUser()
    }

    LaunchedEffect(user) {
        if (user.name.isNotEmpty()) {
            name = user.name
            age = user.age.toString()
            gender = user.gender
        }
    }

    Scaffold(
        topBar = {
            MainTopAppBar(navController = navHostController, title = "프로필 수정", isBackIcon = true)
        },
        bottomBar = {
            BottomButton(text = "입력 완료") {
                // TODO: 입력 완료 시 유저 정보 수정
                // 예: userViewModel.updateUser(name, age.toIntOrNull() ?: 0, gender)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .size(96.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(ColorsLight.lightGrayColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = ColorsLight.grayColor,
                        modifier = Modifier.size(36.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(ColorsLight.darkGrayColor)
                        .clickable { /* 이미지 변경 */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "이미지 변경", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(Modifier.height(32.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("이름") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = age, // String 타입
                onValueChange = { age = it }, // it은 String 타입
                label = { Text("나이") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("성별", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.width(16.dp))
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
            Spacer(Modifier.height(16.dp))
        }
    }
}