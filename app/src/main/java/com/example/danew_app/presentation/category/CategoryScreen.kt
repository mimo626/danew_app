package com.example.danew.presentation.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.category.NewsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CategoryScreen(viewModel: NewsViewModel = viewModel()) {
    val newsList = viewModel.newsList
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.fetchNewsByCategory("technology") // 예시 category
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        errorMessage?.let {
            Text("오류: $it", color = Color.Red)
        }

        LazyColumn {
            items(newsList) { news ->
                NewsItem(news)
                Divider()
            }
        }
    }
}

@Composable
fun NewsItem(news: NewsModel) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(news.title ?: "제목 없음", fontWeight = FontWeight.Bold)
        Text(news.description ?: "내용 없음", maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(news.pubDate ?: "", style = MaterialTheme.typography.bodySmall)
    }
}