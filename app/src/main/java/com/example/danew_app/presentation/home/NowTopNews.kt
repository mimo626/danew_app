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
import com.example.danew.presentation.home.Article

// 현재 Top 뉴스 위젯
@Composable
fun NowTopNews(sectionTitle:String, articles: List<Article>){
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
        articles.get(0).thumbnailUrl?.let { TopImageCard(
            imageUrl = it, title =  articles.get(0).title)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            articles.forEach {
                NewsCard(it)
            }
        }
    }
}