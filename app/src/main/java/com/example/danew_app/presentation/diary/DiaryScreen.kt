package com.example.danew.presentation.diary

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.gloabals.formatDateToString
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.DiaryViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

fun Int.isLeapYear(): Boolean {
    return (this % 4 == 0 && this % 100 != 0) || (this % 400 == 0)
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryScreen(navController: NavHostController,) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    val today = remember { LocalDate.now() }
    var selectedDate by remember { mutableStateOf(today) }
    val isLeap = selectedDate.year.isLeapYear()
    val lastDayOfMonth = (selectedDate.month).length(isLeap)
    val days = (1..lastDayOfMonth).toList()

    val monthsList = remember {
        val current = YearMonth.now()
        val list = mutableListOf<YearMonth>()
        for (i in 0..12) { // 작년까지 포함한 13개월
            list.add(current.minusMonths(i.toLong()))
        }
        list
    }

    val listState = rememberLazyListState()

    val diaryViewModel: DiaryViewModel = hiltViewModel()

    LaunchedEffect(selectedDate) {
        diaryViewModel.getCreatedAt = selectedDate.toString()
        diaryViewModel.getDiaryByDate() // 서버나 DB에서 바로 가져오기
    }

    // 초기 스크롤: 오늘 날짜가 앞으로 오도록
    LaunchedEffect(Unit) {
        val todayIndex = selectedDate.dayOfMonth - 1
        listState.scrollToItem(todayIndex)
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(navController = navController, title = "기록", icon = Icons.Default.MoreVert,
               isHome = false){
                //TODO 수정 필요
                navController.navigate("")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // 바텀시트
            if (showSheet) {
                ModalBottomSheet(
                    containerColor = ColorsLight.whiteColor,
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .height(300.dp)
                            .verticalScroll(rememberScrollState()),

                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "월 선택하기",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        monthsList.forEach { month ->
                            val formatted = month.format(DateTimeFormatter.ofPattern("yyyy년 M월"))
                            Text(
                                text = formatted,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedDate = month.atDay(1)
                                        showSheet = false
                                    }
                                    .padding(vertical = 12.dp, horizontal = 20.dp)
                            )
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        showSheet = true
                    }
            ) {
                Text(
                    text = "${selectedDate.monthValue}월",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowDropDown, contentDescription = "월 선택")
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Horizontal Day Selector (1~31)
            LazyRow(state = listState) {
                items(days) { day ->
                    val thisDate = selectedDate.withDayOfMonth(
                        day.coerceAtMost(
                            selectedDate.month.length(selectedDate.isLeapYear)
                        )
                    )
                    val isSelected = selectedDate.dayOfMonth == day

                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) ColorsLight.secondaryColor else ColorsLight.whiteColor)
                            .clickable { selectedDate = thisDate }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$day",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = if (isSelected) ColorsLight.primaryColor else ColorsLight.grayColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 날짜
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(formatDateToString(selectedDate), fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if(diaryViewModel.isLoading){
                Box (modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator()
                }
            }else {
                // 로딩 끝
                val contentText = diaryViewModel.diary?.content ?: "뉴스를 통해 어떤 것을 느끼셨나요?"
                Box (modifier = Modifier
                    .fillMaxSize()
                    .clickable{
                        val dateStr = selectedDate.format(DateTimeFormatter.ISO_DATE) // "yyyy-MM-dd" 형식
                        navController.navigate("diary/${dateStr}")
                    }
                ) {
                    Text(
                        contentText,
                        color = ColorsLight.darkGrayColor,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
