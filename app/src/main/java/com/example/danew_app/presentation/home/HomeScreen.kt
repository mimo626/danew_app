package com.example.danew.presentation.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.danew_app.core.widget.CustomLoadingIndicator
import com.example.danew_app.core.widget.LazyLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.data.entity.NewsDetailType
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.home.MainImageCard
import com.example.danew_app.presentation.home.NewsItem
import com.example.danew_app.presentation.home.NowTopNews
import com.example.danew_app.presentation.home.SearchBar
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.presentation.viewmodel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavHostController,newsViewModel: NewsViewModel) {
    val userViewModel: UserViewModel = hiltViewModel()
    val user by userViewModel.getUserData.collectAsState()

    // Paging 아이템 수집
    val newsPagingItems = newsViewModel.recommendedNewsFlow.collectAsLazyPagingItems()

    // 상수 정의
    val NEWS_ITEMS_PER_GROUP = 8
    val TOP_NEWS_ITEMS_COUNT = 4
    val TOTAL_ITEMS_PER_SECTION = NEWS_ITEMS_PER_GROUP + TOP_NEWS_ITEMS_COUNT
    val topNewsKeyword = "주요"

    LaunchedEffect(Unit) {
        userViewModel.getUser()
    }

    LaunchedEffect(user) {
        // user가 로드되었다면(혹은 null에서 바뀌었다면) 토큰도 바뀌었을 확률이 높으므로 갱신 요청
        Log.d("추천 News 업데이트", "유저 정보 변경 감지 -> 뉴스 갱신 요청")
        newsViewModel.refreshUserToken()
    }

    LaunchedEffect(newsPagingItems) {
        snapshotFlow { newsPagingItems.itemSnapshotList.items }
            .collect { list ->
                Log.d("News", "현재 로드된 아이템 수: ${list.size}")
                list.forEachIndexed { index, item ->
                    Log.d("News", "[$index] ${item.title}")
                }
            }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MainTopAppBar(
                navController = navController,
                title = "DANEW",
                icon = Icons.Default.Notifications,
                isHome = true
            ) {
                navController.navigate("alarm")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                SearchBar(navHostController = navController)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 1. 초기 로딩 상태 처리
            if (newsPagingItems.loadState.refresh is LoadState.Loading) {
                item { LazyLoadingIndicator() }
            }

            // 2. 데이터가 있을 때 렌더링 시작
            if (newsPagingItems.itemCount > 0) {

                // --- 상단 배너 처리 ---
                val firstBannerItem = newsPagingItems.peek(0)
                val isBannerShown = firstBannerItem?.imageUrl != null

                if (isBannerShown) {
                    item(key = "banner_header") {
                        // 배너 클릭 시에도 ID로 이동
                        MainImageCard(
                            newsModel = firstBannerItem!!,
                            navController = navController
                        )
                        Spacer(modifier = Modifier.height(28.dp))
                    }
                }

                // --- 메인 리스트 처리 ---
                // 배너가 있으면 1번 인덱스부터, 없으면 0번부터 리스트 시작
                val startIndex = if (isBannerShown) 1 else 0

                items(
                    count = newsPagingItems.itemCount - startIndex,
                    // key 최적화: Paging 아이템의 고유 ID 사용 (매우 중요)
                    key = newsPagingItems.itemKey { it.newsId }
                ) { relativeIndex ->

                    // PagingData 내부의 실제 인덱스 계산
                    val actualIndex = relativeIndex + startIndex
                    val item = newsPagingItems[actualIndex]

                    if (item != null) {
                        // 섹션 내 위치 계산 (0 ~ 11)
                        val positionInGroup = (actualIndex - startIndex) % TOTAL_ITEMS_PER_SECTION

                        // 1) 추천 뉴스 헤더 (각 섹션의 시작 부분)
                        if (positionInGroup == 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "${user.name}님을 위한 추천 뉴스",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 20.dp),
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // 2) 일반 뉴스 아이템 (0~7번)
                        if (positionInGroup < NEWS_ITEMS_PER_GROUP) {
                            NewsItem(
                                newsModel = item,
                                onItemClick = {
                                    // 💡 ID를 사용하여 상세 페이지로 이동
                                    navController.navigate("details/${NewsDetailType.HOME}/${item.newsId}")
                                }
                            )
                        }
                        // 3) Top 뉴스 위젯 (8번째 자리에서 4개를 묶어서 보여줌)
                        else if (positionInGroup == NEWS_ITEMS_PER_GROUP) {

                            // 현재 위치부터 4개의 아이템 수집
                            val topNewsList = remember(actualIndex, newsPagingItems.itemSnapshotList) {
                                val list = mutableListOf<NewsModel>()
                                for (i in 0 until TOP_NEWS_ITEMS_COUNT) {
                                    // peek을 사용하여 불필요한 로드 방지하면서 데이터 확인
                                    newsPagingItems.peek(actualIndex + i)?.let { list.add(it) }
                                }
                                list
                            }

                            // 데이터가 4개 다 모였거나, 리스트 끝이라 남은거라도 있을 때 표시
                            if (topNewsList.isNotEmpty()) {
                                NowTopNews(
                                    title = "현재 TOP $topNewsKeyword 뉴스",
                                    newsList = topNewsList,
                                    onItemClick = { clickedIndexInWidget ->
                                        // clickedIndexInWidget: 위젯 내부 인덱스 (0, 1, 2, 3)

                                        // 💡 [중요 수정] topNewsList는 새로 만든 리스트이므로
                                        // global index가 아닌 0~3 인덱스로 접근해야 함!
                                        val selectedNews = topNewsList.getOrNull(clickedIndexInWidget)

                                        selectedNews?.let { news ->
                                            // ID로 이동
                                            navController.navigate("details/${NewsDetailType.HOME}/${news.newsId}")
                                        }
                                    }
                                )
                            }
                        }
                        // 4) 9, 10, 11번 인덱스는 위젯에 포함되었으므로 빈 공간으로 처리 (렌더링 X)
                    }
                }
            }

            // 3. 추가 로딩(Append) 상태 처리
            newsPagingItems.apply {
                when (loadState.append) {
                    is LoadState.Loading -> item {
                        CustomLoadingIndicator()
                    }
                    is LoadState.Error -> item {
                        Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                            Text("오류 발생", color = MaterialTheme.colorScheme.onError,
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}