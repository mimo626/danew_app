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
    initialNewsId: String,
    listType:String,
    categoryName:String?,
    navHostController: NavHostController,
    newsViewModel: NewsViewModel
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
        else -> { // LoadState.NotLoading (성공)

            val pagerState = rememberPagerState(
                initialPage = 0, // 일단 0으로 시작 (나중에 ID로 찾아서 이동함)
                pageCount = { newsPagingItems.itemCount },
            )

            // 💡 핵심 로직 변경: ID로 인덱스 찾기
            LaunchedEffect(newsPagingItems.itemCount, initialNewsId) {
                if (newsPagingItems.itemCount > 0) {
                    // 목록을 순회하며 initialNewsId와 일치하는 뉴스의 위치(index)를 찾음
                    var targetIndex = -1
                    for (i in 0 until newsPagingItems.itemCount) {
                        // peek(i)는 데이터를 로드하지 않고 확인만 함 (안전함)
                        val item = newsPagingItems.peek(i)
                        if (item?.newsId == initialNewsId) {
                            targetIndex = i
                            break // 찾았으면 중단
                        }
                    }

                    // 찾았고, 현재 페이지가 그 위치가 아니라면 이동
                    if (targetIndex != -1 && pagerState.currentPage != targetIndex) {
                        pagerState.scrollToPage(targetIndex)
                        Log.d("News 상세", "ID($initialNewsId)를 찾음 -> 인덱스 $targetIndex 로 이동")
                    } else if (targetIndex == -1) {
                        Log.d("News 상세", "해당 ID($initialNewsId)를 목록에서 찾을 수 없음")
                        // 필요하다면 여기서 찾지 못했을 때 처리 (예: 첫 페이지 보여주기)
                    }
                }
            }
            // 💡 추가: 로드는 성공했지만 아이템이 0개일 경우 Pager를 그리면 안 됩니다.
            if (newsPagingItems.itemCount == 0) {
                Scaffold(containerColor = ColorsLight.whiteColor) { padding ->
                    Text(
                        text = "표시할 뉴스가 없습니다.",
                        modifier = Modifier.padding(padding).fillMaxSize().padding(20.dp),
                    )
                }
            } else {
                // 아이템이 1개 이상일 때만 Pager를 그립니다.
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                ) { pageIndex ->

                    val news = newsPagingItems[pageIndex]
                    Log.d("News 상세: ", "${pageIndex} ${news}")

                    if (news != null) {
                        NewsDetailScreen(
                            news = news,
                            navHostController = navHostController
                        )
                    } else {
                        CustomLinearProgressIndicator(progress = 2.0F)
                    }
                }
            }
        }
    }
}
