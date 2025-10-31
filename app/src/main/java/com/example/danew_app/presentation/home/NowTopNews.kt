package com.example.danew_app.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.danew_app.domain.model.NewsModel

// 현재 Top 뉴스 위젯
@Composable
fun NowTopNews(
    title: String,
    newsList: List<NewsModel>, // 4개의 뉴스가 담긴 원본 리스트
    onItemClick: (Int) -> Unit  // (Int) -> Unit 콜백
) {
    // imageUrl 있는 첫 번째 뉴스
    val topNews = newsList.firstOrNull { !it.imageUrl.isNullOrBlank() }
    // 나머지 뉴스들 (topNews 제외)
    val otherNews = newsList.filter { it != topNews }

    Column (
        modifier = Modifier.padding(horizontal = 20.dp)
    ){
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),)

        Spacer(modifier = Modifier.height(16.dp))
        // 나머지 뉴스 중 최대 3개 표시
        LazyRow {
            items(otherNews.take(3)) { news ->
                // 1. navController 대신 onClick 람다 전달
                NewsCard(
                    newsModel = news,
                    onClick = {
                        // 원본 newsList에서 이 news의 인덱스를 찾아 콜백 실행
                        val originalIndex = newsList.indexOf(news)
                        if (originalIndex != -1) {
                            onItemClick(originalIndex)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 이미지 있는 뉴스가 있으면 TopImageCard로 표시
        topNews?.let { topNewsItem -> // (변수명 'it' -> 'topNewsItem'으로 변경)
            // 2. navController 대신 onClick 람다 전달
            TopImageCard(
                newsModel = topNewsItem,
                onClick = {
                    // 원본 newsList에서 이 topNewsItem의 인덱스를 찾아 콜백 실행
                    val originalIndex = newsList.indexOf(topNewsItem)
                    if (originalIndex != -1) {
                        onItemClick(originalIndex)
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}