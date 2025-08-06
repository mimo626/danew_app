package com.example.danew.presentation.diary

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.danew_app.core.gloabals.formatDateToString
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.presentation.home.MainTopAppBar
import java.time.LocalDate

fun Int.isLeapYear(): Boolean {
    return (this % 4 == 0 && this % 100 != 0) || (this % 400 == 0)
}

@Composable
fun DiaryScreen() {
    var selectedTab by remember { mutableStateOf("일간") }
    val today = remember { LocalDate.now() }
    var selectedDate by remember { mutableStateOf(today) }
    var selectedMonth by remember { mutableStateOf(today.month.name) }
    val isLeap = selectedDate.year.isLeapYear()
    val lastDayOfMonth = (selectedDate.month).length(isLeap)
    val days = (1..lastDayOfMonth).toList()

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(title = "기록", icon = Icons.Default.MoreVert,
               isHome = false)
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${selectedDate.monthValue}월",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowDropDown, contentDescription = "월 선택")
            }

            Spacer(modifier = Modifier.height(8.dp))


            Spacer(modifier = Modifier.height(12.dp))

            // Horizontal Day Selector (1~31)
            LazyRow {
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

            // 날짜 및 선
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(formatDateToString(selectedDate), fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "뉴스를 통해 어떤 것을 느끼셨나요?",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}
