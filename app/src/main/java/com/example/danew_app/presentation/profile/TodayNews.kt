package com.example.danew_app.presentation.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import com.example.danew_app.presentation.home.NewsCard


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.viewmodel.TodayNewsViewModel

// 오늘 본 뉴스 위젯
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayNews(sectionTitle:String, navController: NavHostController){
    val todayNewsViewModel: TodayNewsViewModel = hiltViewModel()
    val todayNews by todayNewsViewModel.todayNews.collectAsState()

    LaunchedEffect(Unit) {
        todayNewsViewModel.getNews()
    }

    Column (modifier = Modifier.padding(horizontal = 20.dp)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(sectionTitle, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("전체보기", color = Color.Blue, fontSize = 12.sp,
                modifier = Modifier.clickable{
                    navController.navigate("todayNews")
                })
        }
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow {
            if(todayNews.isEmpty()){
                item {
                    Text("아직 오늘 본 뉴스가 없습니다.")
                }
            }
            else{
                items(todayNews) {
                    NewsCard(newsModel = it.toDomain(), onClick = {
                        navController.navigate("details/noScroll/${it.newsId}")
                    })
                }
            }
        }
    }
}