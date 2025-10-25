package com.example.danew.presentation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.CustomLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.home.MainImageCard
import com.example.danew_app.presentation.home.NewsItem
import com.example.danew_app.presentation.home.NowTopNews
import com.example.danew_app.presentation.home.SearchBar
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.presentation.viewmodel.UserViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val newsViewModel: NewsViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()
    val user by userViewModel.getUserData.collectAsState()
    val newsPagingItems = newsViewModel.recommendedNewsFlow.collectAsLazyPagingItems()

    // 아이템 그룹화 상수 정의
    val NEWS_ITEMS_PER_GROUP = 8
    val TOP_NEWS_ITEMS_COUNT = 4
    val TOTAL_ITEMS_PER_SECTION = NEWS_ITEMS_PER_GROUP + TOP_NEWS_ITEMS_COUNT

    // NowTopNews에 표시할 키워드는 임의로 설정하거나 ViewModel에서 가져와야 합니다.
    val topNewsKeyword = "주요"

    LaunchedEffect(Unit) {
        userViewModel.getUser()   // 화면 진입 시 한 번 실행
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
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

            // 초기 로딩 중일 때 로딩 인디케이터 표시
            if (newsPagingItems.loadState.refresh is LoadState.Loading) {
                item {
                    CustomLoadingIndicator()
                }
            }

            // Paging 아이템의 총 개수가 0보다 클 때만 접근 시도
            if (newsPagingItems.itemCount > 0) {

                // 1. 첫 번째 배너 (MainImageCard)
                val firstBannerItem = newsPagingItems.peek(0)
                val isBannerShown = firstBannerItem != null && firstBannerItem.imageUrl != null

                if (isBannerShown) {
                    item(key = "banner_0") {
                        MainImageCard(firstBannerItem!!, navController)
                        Spacer(modifier = Modifier.height(28.dp))
                    }
                }

                // 2. 나머지 Paging 아이템 리스트
                val startIndex = if (isBannerShown) 1 else 0

                items(
                    count = newsPagingItems.itemCount - startIndex,
                    key = newsPagingItems.itemKey { it.newsId }
                ) { relativeIndex ->

                    // 실제 PagingItems에서의 인덱스
                    val actualIndex = relativeIndex + startIndex
                    val item = newsPagingItems[actualIndex]

                    if (item != null) {

                        // 현재 아이템이 섹션 내에서 차지하는 위치 (0부터 11까지 반복)
                        val positionInGroup = (actualIndex - startIndex) % TOTAL_ITEMS_PER_SECTION
                        val topNewsStartIndex = NEWS_ITEMS_PER_GROUP // 8

                        // "민주님을 위한 추천 뉴스" 헤더 표시 로직 추가
                        if (positionInGroup == 0) {
                            Spacer(modifier = Modifier.height(8.dp)) // 상단 여백 추가
                            Text(
                                "${user.name}님을 위한 추천 뉴스",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // 8개 NewsItem 섹션: positionInGroup이 0부터 7까지
                        if (positionInGroup < topNewsStartIndex) {
                            NewsItem(item, navController)
                        }

                        // 4개 NowTopNews 섹션 (positionInGroup 8, 9, 10, 11)
                        else {
                            // 8번째 아이템일 때만 NowTopNews 위젯을 표시합니다.
                            if (positionInGroup == topNewsStartIndex) { // 8번째 아이템일 때

                                // NowTopNews에 사용할 4개의 뉴스 아이템을 Paging Items에서 가져옵니다.
                                val currentSectionStart = actualIndex
                                val topNewsList = mutableListOf<NewsModel>()

                                for (i in 0 until TOP_NEWS_ITEMS_COUNT) {
                                    // peek(index)를 사용하여 아이템을 가져옵니다.
                                    val newsData = newsPagingItems.peek(currentSectionStart + i)
                                    if (newsData != null) {
                                        topNewsList.add(newsData)
                                    }
                                }

                                // 4개의 아이템을 NowTopNews 위젯에 전달하여 하나의 섹션을 만듭니다.
                                NowTopNews("현재 TOP $topNewsKeyword 뉴스", topNewsList, navController)
                                Spacer(modifier = Modifier.height(28.dp)) // 섹션 후 여백
                            }
                            // positionInGroup이 9, 10, 11인 경우: 아무것도 렌더링하지 않음 (NowTopNews 위젯에 자리를 양보).
                        }
                    }
                }
            }

            // 추가 로딩/에러 (스크롤 끝에 더 로드 중/오류 표시)
            newsPagingItems.apply {
                when (loadState.append) {
                    is LoadState.Loading -> item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentSize(Alignment.Center)
                        )
                    }
                    is LoadState.Error -> item {
                        Text(
                            text = "뉴스 로딩 중 오류가 발생했습니다.",
                            modifier = Modifier.fillMaxWidth().padding(16.dp).wrapContentSize(Alignment.Center)
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}