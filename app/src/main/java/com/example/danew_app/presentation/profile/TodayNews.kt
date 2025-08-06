package com.example.danew_app.presentation.profile

import com.example.danew_app.presentation.home.NewsCard
import com.example.danew_app.presentation.home.TopImageCard


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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danew_app.domain.model.NewsModel

// 오늘 본 뉴스 위젯
@Composable
fun TodayNews(sectionTitle:String, newsList: List<NewsModel>){
    Column (modifier = Modifier.padding(horizontal = 16.dp)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(sectionTitle, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("전체보기", color = Color.Blue, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow {
            items(newsList) { // 임시로 2개 뉴스 카드
                NewsCard(newsModel = it)
            }
        }
    }
}