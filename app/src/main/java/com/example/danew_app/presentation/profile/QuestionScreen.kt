package com.example.danew_app.presentation.profile


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.LazyLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.QuestionViewModel

@Composable
fun QuestionScreen(navController: NavHostController) {
    val questionViewModel: QuestionViewModel = hiltViewModel()
    val questionList = questionViewModel.questionList
    val isLoading = questionViewModel.isLoading
    val errorMessage = questionViewModel.errorMessage

    var expandedIds by remember { mutableStateOf(setOf<String>()) }

    LaunchedEffect(Unit) {
        questionViewModel.getQuestionList()
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                navController = navController,
                title = "자주 묻는 질문",
                icon = Icons.Default.Settings,
                isBackIcon = true,
                isHome = false)
            {
                navController.navigate("settings")
            }
        },
        bottomBar = {
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    item {
                        LazyLoadingIndicator(padding)
                    }
                }

                questionList.isNotEmpty() -> {
                    item { Spacer(modifier = Modifier.height(16.dp)) }

                    // [2] 리스트 아이템 구성
                    items(questionList) { question ->
                        // 현재 아이템이 열려있는 상태인지 확인
                        // (announce.id는 실제 모델의 고유 ID 변수명으로 변경해주세요. 예: announceId)
                        val isExpanded = expandedIds.contains(question.questionId)

                        Column(modifier = Modifier.fillMaxWidth()) {
                            // 질문 아이템
                            MyPageMenuItem(
                                title = question.question,
                                fontSize = 16) {
                                // [3] 클릭 시 접었다 폈다 하는 로직
                                expandedIds = if (isExpanded) {
                                    expandedIds - question.questionId // 이미 열려있으면 제거 (닫기)
                                } else {
                                    expandedIds + question.questionId // 닫혀있으면 추가 (열기)
                                }
                            }

                            // [4] 답변 영역 (애니메이션 적용)
                            AnimatedVisibility(
                                visible = isExpanded,
                                enter = expandVertically() + fadeIn(), // 위에서 아래로 펼쳐짐 + 페이드인
                                exit = shrinkVertically() + fadeOut()  // 아래에서 위로 접힘 + 페이드아웃
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp)
                                        .background(ColorsLight.lightGrayColor) // 약간 회색 배경으로 구분
                                        .padding(16.dp)
                                ) {
                                    // 답변 내용 표시 (announce.answer는 실제 답변 변수명으로 변경)
                                    Text(
                                        text = question.answer,
                                        color = ColorsLight.blackColor,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(28.dp)) }
                }
                else -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(padding),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("자주 묻는 질문이 없습니다")
                        }
                    }
                }
            }
        }
    }
}