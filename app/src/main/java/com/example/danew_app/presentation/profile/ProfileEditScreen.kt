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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.CustomRadioButton
import com.example.danew_app.core.widget.CustomUnderlinedTextField
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.data.dto.UpdateUserRequest
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
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(navController = navHostController, title = "프로필 수정", isBackIcon = true)
        },
        bottomBar = {
            BottomButton(text = "입력 완료") {
                val request = UpdateUserRequest(
                    name = if (name.isNotBlank()) name else null,
                    age = age.toIntOrNull(),
                    gender = if (gender.isNotBlank()) gender else null
                )
                userViewModel.updateUser(request)
                navHostController.popBackStack()
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
            Spacer(Modifier.height(24.dp))
            CustomUnderlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = "이름"
            )
            CustomUnderlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = "나이"
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
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
            Spacer(Modifier.height(16.dp))
        }
    }
}