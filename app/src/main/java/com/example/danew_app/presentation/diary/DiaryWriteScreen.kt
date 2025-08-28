package com.example.danew.presentation.diary
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.DiaryViewModel
import java.time.LocalDate

@Composable
fun DiaryWriteScreen(date: String, navHostController: NavHostController) {
    val selectedDate = date
    var inputText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val diaryViewModel:DiaryViewModel = hiltViewModel()

    // 화면 진입 시 포커스 요청
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(diaryViewModel.saveSuccess) {
        if (diaryViewModel.saveSuccess) {
            navHostController.navigate("diary")
        }
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                navController = navHostController,
                title = "기록하기",
                isBackIcon = true
            )
        },
        bottomBar = {
            // 하단 고정 버튼
            BottomButton(
                text = "입력 완료",
                textColor = ColorsLight.whiteColor,
                backgroundColor = ColorsLight.primaryColor
            ) {
                //TODO 유저 기록 데이터에 저장
                diaryViewModel.content = inputText
                diaryViewModel.saveCreatedAt = selectedDate
                diaryViewModel.saveDiary()
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(24.dp))
            // 날짜 표시
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(selectedDate, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 입력 영역
            BasicTextField(
                value = inputText,
                onValueChange = { inputText = it },
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .focusRequester(focusRequester),
                decorationBox = { innerTextField ->
                    if (inputText.isEmpty()) {
                        Text(
                            "뉴스를 통해 어떤 것을 느끼셨나요?",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

