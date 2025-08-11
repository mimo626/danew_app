package com.example.danew.presentation.category

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.unit.dp
import com.example.danew_app.presentation.category.NewsViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.sp
import com.example.danew_app.presentation.category.NewsCategory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.presentation.home.MainTopAppBar
import com.example.danew_app.presentation.home.NewsItem
import com.example.danew_app.presentation.home.NowTopNews

val newsCategoryKr = NewsCategory.categoryKrToEn.keys.toList()

@Composable
fun CategoryScreen(navController: NavHostController, viewModel: NewsViewModel = hiltViewModel()) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val listState = rememberLazyListState()
    val newsList = viewModel.newsList
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    // 탭 인덱스 변경 시 API 호출
    LaunchedEffect(selectedTabIndex) {
        val krCategory = newsCategoryKr[selectedTabIndex]
        val enCategory = NewsCategory.categoryKrToEn[krCategory] ?: "top"
        viewModel.fetchNewsByCategory(enCategory)
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                navController = navController,
                title = "카테고리",
                icon = Icons.Default.Notifications,
                isHome = false
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding).padding(horizontal = 20.dp),
            state = listState
        ) {
            // 탭
            item {
                ScrollableTabRow(
                    containerColor = ColorsLight.whiteColor,
                    selectedTabIndex = selectedTabIndex,
                    edgePadding = 0.dp,
                    indicator = { tabPositions ->
                        val currentTabPosition = tabPositions[selectedTabIndex]
                        Box(
                            Modifier
                                .tabIndicatorOffset(currentTabPosition)
                                .padding(horizontal = 16.dp)
                                .height(2.dp)
                                .background(ColorsLight.darkGrayColor, RoundedCornerShape(1.dp))
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
            }

            // 로딩
            if (isLoading && newsList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = ColorsLight.grayColor)
                    }
                }
            }

            // 에러 메시지
            if (errorMessage != null) {
                item {
                    Text("오류: $errorMessage", color = Color.Red)
                }
            }

            // NowTopNews
            if (newsList.isNotEmpty()) {
                item {
                    NowTopNews(
                        sectionTitle = "실시간 인기 뉴스",
                        newsList = newsList.take(4),
                        navController = navController
                    )
                    Spacer(Modifier.height(36.dp))
                    Text("${newsCategoryKr[selectedTabIndex]} 이슈 뉴스", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // 일반 뉴스 리스트
            items(items = newsList.drop(4)) { news ->
                NewsItem(news, navController)
            }

            // 로딩 인디케이터 (페이지네이션)
            if (isLoading && newsList.isNotEmpty()) {
                item {
                    CircularProgressIndicator(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }

        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastVisibleIndex ->
                    if (lastVisibleIndex != null &&
                        lastVisibleIndex >= newsList.lastIndex - 2 &&
                        !isLoading
                    ) {
                        val krCategory = newsCategoryKr[selectedTabIndex]
                        val enCategory = NewsCategory.categoryKrToEn[krCategory] ?: "top"
                        Log.d("NewsViewModel", "!!!!!! Load more")
                        viewModel.fetchNewsByCategory(enCategory, loadMore = true)
                    }
                    else{
                        Log.d("NewsViewModel", "????? ${lastVisibleIndex}")
                        Log.d("NewsViewModel", "????? ${newsList.lastIndex}")

                    }
                }
        }

    }
}
