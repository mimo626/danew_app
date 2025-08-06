package com.example.danew_app.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danew_app.domain.model.NewsModel

// 가로로 뉴스 리스트(작은 사진)
@Composable
fun NewsList(sectionTitle: String, newsList: List<NewsModel>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(sectionTitle, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        newsList.forEach {
            NewsItem(it)
        }
    }
}