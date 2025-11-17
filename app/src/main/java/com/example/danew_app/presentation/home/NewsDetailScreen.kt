package com.example.danew_app.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Build
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
import com.example.danew_app.core.widget.CustomLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.core.widget.ShareButton
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.viewmodel.BookmarkViewModel
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.presentation.viewmodel.TodayNewsViewModel

// 2. 개별 뉴스 페이지 UI (기존 NewsDetailScreen의 Scaffold와 LazyColumn)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailScreen(
    news: NewsModel,
    navHostController: NavHostController,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    todayNewsViewModel: TodayNewsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val newsLink = news.link
    val newsViewModel: NewsViewModel = hiltViewModel()

    val newsList by newsViewModel.newsListById.collectAsState()
    val isLoading = newsViewModel.isLoading
    val errorMessage = newsViewModel.errorMessage
    val isBookmarked by bookmarkViewModel.isBookmark.collectAsState()

    LaunchedEffect(news.newsId) {
        newsViewModel.fetchNewsById(id = news.newsId)
        bookmarkViewModel.checkBookmark(news.newsId)
        todayNewsViewModel.addNews(news)
    }

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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start
        ) {

            news.category?.firstOrNull()?.let { category ->
                item {
                    Box(
                        modifier = Modifier
                            // ... (기존 스타일)
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

            item {
                Text(
                    text = news.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            news.creator?.firstOrNull()?.let { creator ->
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        // ... (기존 작성자 UI) ...
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

            // [로드 필요] AI 요약 (ViewModel의 상태에 따라 분기 처리)
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

                when {
                    isLoading -> {
                        // 요약본 섹션만 로딩 중 표시
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CustomLoadingIndicator(padding = paddingValues) // 또는 CustomLoadingIndicator
                        }
                    }
                    errorMessage != null -> {
                        // 요약본 섹션만 에러 표시
                        Text(
                            text = "요약본을 불러오는 데 실패했습니다: $errorMessage",
                            color = Color.Red,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    newsList != null -> {
                        // 성공: ViewModel에서 받아온 상세 데이터(newsList)를 사용
                        // **주의: news.description이 아닌,
                        // fetchNewsById로 가져온 객체(newsList)의 description을 사용해야 합니다.**
                        Text(
                            text = newsList!!.description, // <-- 'newsList'의 데이터 사용
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }
            }

            // 5. [즉시 표시] Paging으로 받은 'news' 객체의 데이터 (이미지)
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

            // 6. [즉시 표시] Paging으로 받은 'news' 객체의 데이터 (원문 링크 및 날짜)
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ){
                    // ... (기존 원문 보기 UI) ...
                    Text("뉴스 원문 보기",
                        color =  ColorsLight.blueColor,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        modifier = Modifier
                            .clickable{
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsLink))
                                context.startActivity(intent)
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = news.pubDate,
                        fontSize = 14.sp,
                        color = ColorsLight.grayColor,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp)) // 하단 여백 추가
            }
        }
    }
}
