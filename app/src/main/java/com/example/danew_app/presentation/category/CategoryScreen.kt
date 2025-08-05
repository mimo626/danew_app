package com.example.danew.presentation.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.presentation.category.NewsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.category.NewsCategory
import androidx.hilt.navigation.compose.hiltViewModel

val newsCategoryKr = NewsCategory.categoryEnToKr.keys.toList()

@Composable
fun CategoryScreen(viewModel: NewsViewModel = hiltViewModel()) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val newsList = viewModel.newsList
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    // 탭 인덱스 변경 시 API 호출
    LaunchedEffect(selectedTabIndex) {
        val krCategory = newsCategoryKr[selectedTabIndex]
        val enCategory = NewsCategory.categoryKrToEn[krCategory] ?: "top"
        viewModel.fetchNewsByCategory(enCategory)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 탭 UI
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            }
        ) {
            newsCategoryKr.forEachIndexed { index, category ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            category,
                            color = if (selectedTabIndex == index) Color.Black else Color.Gray,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 로딩
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // 에러 메시지
        errorMessage?.let {
            Text("오류: $it", color = Color.Red)
        }

        // 뉴스 리스트
        LazyColumn {
            items(newsList) { news ->
                val category = newsCategoryKr[selectedTabIndex]
                NewsItem(news = news, category = category)
            }
        }
    }
}

@Composable
fun NewsItem(news: NewsModel, category: String) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(news.title, fontWeight = FontWeight.Bold)
        Text(news.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(news.pubDate, style = MaterialTheme.typography.bodySmall)
    }
}