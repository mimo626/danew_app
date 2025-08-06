package com.example.danew_app.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danew_app.domain.model.NewsModel

// 현재 Top 뉴스 위젯
@Composable
fun NowTopNews(sectionTitle:String, newsList: List<NewsModel>){
    Column (modifier = Modifier.padding(horizontal = 16.dp)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(sectionTitle, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("전체보기", color = Color.Blue, fontSize = 12.sp)
        }
        newsList.get(0).imageUrl?.let {
            TopImageCard(newsList.get(0))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            newsList.forEach {
                NewsCard(it)
            }
        }
    }
}