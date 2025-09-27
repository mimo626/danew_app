package com.example.danew.presentation.category

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.sp
import com.example.danew_app.presentation.category.NewsCategory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.CustomLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.home.NewsItem
import com.example.danew_app.presentation.home.NowTopNews
import kotlinx.coroutines.flow.distinctUntilChanged

val newsCategoryKr = NewsCategory.categoryKrToEn.keys.toList()

@Composable
fun CategoryScreen(navController: NavHostController, viewModel: NewsViewModel = hiltViewModel()) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val listState = rememberLazyListState()
    val topNewsCount = 4
    val newsPagingItems = viewModel.newsPagingData.collectAsLazyPagingItems()
    val totalItemsCount = newsPagingItems.itemCount


    // 탭 인덱스 변경 시 API 호출
    LaunchedEffect(selectedTabIndex) {
        val krCategory = newsCategoryKr[selectedTabIndex]
        val enCategory = NewsCategory.categoryKrToEn[krCategory] ?: "top"
        viewModel.loadNews(enCategory)
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                navController = navController,
                title = "카테고리",
                icon = Icons.Default.Notifications,
                isHome = false
            ){
                navController.navigate("alarm")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
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
                                    text = category,
                                    color = if (selectedTabIndex == index) Color.Black else Color.Gray,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            // 상위 4개 뉴스
            item {
                val topNews = newsPagingItems.itemSnapshotList.items.take(topNewsCount)
                NowTopNews(
                    sectionTitle = "실시간 인기 뉴스",
                    newsList = topNews,
                    navController = navController
                )
                Spacer(Modifier.height(36.dp))
                Text(
                    "${newsCategoryKr[selectedTabIndex]} 이슈 뉴스",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // 나머지 뉴스 (총 아이템 수 > topNewsCount일 때만)
            if (totalItemsCount > topNewsCount) {
                items(totalItemsCount - topNewsCount) { index ->
                    val item = newsPagingItems[index + topNewsCount]
                    if (item != null) {
                        NewsItem(item, navController)
                    }
                }
            }

            // 로딩 표시
            newsPagingItems.apply {
                when (loadState.append) {
                    is LoadState.Loading -> item { CustomLoadingIndicator() }  // 더 로드 중
                    is LoadState.Error -> item { Text("오류") }
                    else -> {}
                }

                when (loadState.refresh) {
                    is LoadState.Loading -> item { CustomLoadingIndicator() } // 초기 로딩
                    is LoadState.Error -> item { Text("오류") }
                    else -> {}
                }
            }
        }
    }
}
