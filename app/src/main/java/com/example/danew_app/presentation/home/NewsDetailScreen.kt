package com.example.danew.presentation.home
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.core.widget.ShareButton
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.viewmodel.BookmarkViewModel
import com.example.danew_app.presentation.viewmodel.TodayNewsViewModel
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.remember
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.danew_app.core.widget.CustomLinearProgressIndicator
import com.example.danew_app.core.widget.CustomLoadingIndicator

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailScreen(
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
            // 오류 발생 시 (오류 UI 처리)
            Scaffold(containerColor = ColorsLight.whiteColor) { padding ->
                Text(
                    text = "뉴스를 불러오는 데 실패했습니다.",
                    modifier = Modifier.padding(padding).fillMaxSize().padding(20.dp),
                    color = Color.Red
                )
            }
        }
        else -> {
            // 로딩 성공: VerticalPager 표시
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) { pageIndex -> // 현재 페이지의 index

                val news = newsPagingItems[pageIndex]

                if (news != null) {
                    // 2. 실제 UI를 그리는 NewsDetailPage 호출
                    NewsDetailPage(
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


// 2. 개별 뉴스 페이지 UI (기존 NewsDetailScreen의 Scaffold와 LazyColumn)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailPage(
    news: NewsModel,
    navHostController: NavHostController,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(), // 각 페이지가 자신의 ViewModel을 가짐
    todayNewsViewModel: TodayNewsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val newsLink = news.link

    // --- 이 페이지(뉴스)에 대한 상태 관리 ---
    val isBookmarked by bookmarkViewModel.isBookmark.collectAsState()

    // Pager가 이 페이지로 스크롤될 때(news.newsId가 변경될 때) 실행
    LaunchedEffect(news.newsId) {
        bookmarkViewModel.checkBookmark(news.newsId)
        todayNewsViewModel.addNews(news)
    }
    // --- ---

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                navController = navHostController,
                title = "",
                icon = null,
                isHome = false,
                isBackIcon = true
            )
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                IconButton(
                    onClick = {
                        if (isBookmarked) {
                            bookmarkViewModel.deleteBookmark(news.newsId)
                        } else {
                            bookmarkViewModel.saveBookmark(news)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isBookmarked) "북마크 취소" else "북마크",
                        tint = if (isBookmarked) Color.Red else ColorsLight.darkGrayColor,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                ShareButton(newsLink = news.link)
            }
        }
    ) { paddingValues ->

        // 이 LazyColumn은 *기사 내용이 길 때* 스크롤됩니다.
        // Pager 스크롤과는 다릅니다.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start
        ) {

            // --- 기존 코드와 동일한 LazyColumn 내용 ---

            // 카테고리 태그
            news.category?.firstOrNull()?.let { category ->
                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                            .background(
                                color = ColorsLight.secondaryColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            color = ColorsLight.primaryColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // 뉴스 제목
            item {
                Text(
                    text = news.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            // 작성자
            news.creator?.firstOrNull()?.let { creator ->
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    ColorsLight.lightGrayColor,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("작성자", fontSize = 12.sp, color = ColorsLight.grayColor)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(creator, fontSize = 14.sp, color = ColorsLight.grayColor)
                    }
                }
            }

            // AI 요약
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "AI 뉴스 요약본",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ColorsLight.primaryColor,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(news.description, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 20.dp))
            }

            // 이미지
            news.imageUrl?.let { imageUrl ->
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "뉴스 이미지",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(horizontal = 20.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // 뉴스 원문 보기 및 날짜
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ){
                    Text("뉴스 원문 보기",
                        color =  ColorsLight.blueColor,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        modifier = Modifier
                            .clickable{
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsLink))
                                context.startActivity(intent)
                            }
                    )
                    // 날짜
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = news.pubDate, // (이전에 만든 시간 변환 함수 적용)
                        fontSize = 14.sp,
                        color = ColorsLight.grayColor,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp)) // 하단 여백 추가
            }
        }
    }
}
