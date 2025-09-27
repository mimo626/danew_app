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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.CustomLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.home.NewsItem
import com.example.danew_app.presentation.home.SearchBar
import com.example.danew_app.presentation.viewmodel.NewsViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val newsViewModel: NewsViewModel = hiltViewModel()

    // 1. Paging Data 수집
    // ViewModel 생성 시점에 이미 로드가 시작되었으므로,
    // LaunchedEffect에서 별도로 fetchRecommendedNews를 호출할 필요가 없습니다.
    val newsPagingItems = newsViewModel.recommendedNewsFlow.collectAsLazyPagingItems()

    // LaunchedEffect(Unit) {
    //     // 이 코드는 이제 필요 없습니다.
    //     // viewModel.fetchRecommendedNews(currentUserToken)
    // }

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
        // 실제 화면
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item { SearchBar(navHostController = navController) }

            if (newsPagingItems.loadState.refresh is LoadState.Loading) {
                item {
                    CustomLoadingIndicator() // 상단 로딩 표시
                }
            }

            item {
                Text("민주님을 위한 추천 뉴스", fontWeight = FontWeight.Bold, fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(newsPagingItems.itemCount) { index ->
                val item = newsPagingItems[index]
                if (item != null) {
                    NewsItem(item, navController)
                }
            }

            // 추가 로딩/에러 (스크롤 끝에 더 로드 중/오류 표시)
            newsPagingItems.apply {
                when (loadState.append) {
                    is LoadState.Loading -> item { CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentSize(Alignment.Center)
                    ) }  // 더 로드 중
                    is LoadState.Error -> item { Text("오류") }
                    else -> {}
                }
            }
//            if (isLoading) {
//                // 로딩 화면
//                item{
//                    CustomLoadingIndicator(padding)
//                }
//            }
//            else {
//                item { Spacer(modifier = Modifier.height(16.dp)) }
//
//
//                item { Spacer(modifier = Modifier.height(24.dp)) }

//                // MainImageCard
//                mainImageNews?.let {
//                    item { MainImageCard(it, navController) }
//                }
//
//                item { Spacer(modifier = Modifier.height(28.dp)) }
//
//                // 추천 뉴스
//                if (recommendedNews.isNotEmpty()) {
//                    item { NewsList("민주님을 위한 추천 뉴스", recommendedNews, navController) }
//                    item { Spacer(modifier = Modifier.height(28.dp)) }
//                }
//
//                // 키워드별 TOP 뉴스
//                keywordNews.forEach { (keyword, list) ->
//                    if (list.isNotEmpty()) {
//                        item { NowTopNews("현재 TOP $keyword 뉴스", list, navController) }
//                        item { Spacer(modifier = Modifier.height(28.dp)) }
//                    }
//                }
            }
        }

}


