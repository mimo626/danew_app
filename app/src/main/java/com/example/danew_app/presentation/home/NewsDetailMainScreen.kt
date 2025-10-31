package com.example.danew.presentation.home
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.remember
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.danew_app.core.widget.CustomLinearProgressIndicator
import com.example.danew_app.core.widget.CustomLoadingIndicator
import com.example.danew_app.presentation.home.NewsDetailScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailMainScreen(
    initialIndex: Int,
    listType:String,
    categoryName:String?,
    navHostController: NavHostController,
    newsViewModel: NewsViewModel = hiltViewModel() // ViewModel 주입
) {
    val pagingFlow = remember(listType, categoryName) {
        when (listType) {
            "category" -> {
                newsViewModel.newsByCategory
            }
            "home" -> {
                newsViewModel.recommendedNewsFlow
            }
            else -> {
                // 기본값 (홈)
                newsViewModel.recommendedNewsFlow
            }
        }
    }
    // Paging 데이터 수집
    val newsPagingItems = pagingFlow.collectAsLazyPagingItems()


    // Pager의 상태를 관리 (초기 페이지 설정)
    //TODO 새로운 뉴스 상세로 클릭 시 initialIndex는 변경되는데 화면에 뉴스 업데이트 문제 해결 필요
    val pagerState = rememberPagerState(
        initialPage = initialIndex,
        pageCount = { newsPagingItems.itemCount },
    )

    LaunchedEffect(newsPagingItems.itemCount, initialIndex) {
        if (newsPagingItems.itemCount > 0 &&
            initialIndex < newsPagingItems.itemCount
        ) {
            pagerState.scrollToPage(initialIndex)
            Log.d("News 상세", "초기 페이지 이동: $initialIndex / 총 ${newsPagingItems.itemCount}")
        }
    }


    // Paging 데이터의 로드 상태 확인
    when (newsPagingItems.loadState.refresh) {
        is LoadState.Loading -> {
            // 전체 목록이 로딩 중일 때
            Scaffold(containerColor = ColorsLight.whiteColor) { padding ->
                CustomLoadingIndicator(padding)
            }
        }
        is LoadState.Error -> {
            Scaffold(containerColor = ColorsLight.whiteColor) { padding ->
                Text(
                    text = "뉴스를 불러오는 데 실패했습니다.",
                    modifier = Modifier.padding(padding).fillMaxSize().padding(20.dp),
                    color = Color.Red
                )
            }
        }
        else -> {
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) { pageIndex -> // 현재 페이지의 index

                val news = newsPagingItems[pageIndex]

                if (news != null) {
                    // 2. 실제 UI를 그리는 NewsDetailPage 호출
                    NewsDetailScreen(
                        news = news,
                        navHostController = navHostController
                    )
                } else {
                    // 개별 페이지가 로드 중일 때 (Paging 특성)
                    CustomLinearProgressIndicator(progress = 2.0F)
                }
            }
        }
    }
}
