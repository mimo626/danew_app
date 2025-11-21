package com.example.danew_app.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.danew_app.core.widget.LazyLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.core.widget.ShareButton
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.viewmodel.BookmarkViewModel
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.presentation.viewmodel.TodayNewsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailScreen(
    newsId: String,
    navHostController: NavHostController,
    isPageFocused: Boolean,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    todayNewsViewModel: TodayNewsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val newsViewModel: NewsViewModel = hiltViewModel()

    val newsMap by newsViewModel.newsMap.collectAsState()
    val detailedNews = newsMap[newsId]
    val isLoading = newsViewModel.isLoading
    val errorMessage = newsViewModel.errorMessage
    val isBookmarked by bookmarkViewModel.isBookmark.collectAsState()

    LaunchedEffect(newsId, isPageFocused) {
        if (isPageFocused) {
            // 이 페이지가 현재 주인공일 때만 데이터를 가져옵니다.
            newsViewModel.fetchNewsById(id = newsId)
            bookmarkViewModel.checkBookmark(newsId)
        }
    }
    LaunchedEffect(detailedNews) {
        if (detailedNews != null) {
            todayNewsViewModel.addNews(detailedNews)
        }
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
                if(detailedNews != null){
                    IconButton(
                        onClick = {
                            if (isBookmarked) {
                                bookmarkViewModel.deleteBookmark(detailedNews.newsId)
                            } else {
                                bookmarkViewModel.saveBookmark(detailedNews)
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
                    ShareButton(newsLink = detailedNews.link)
                } else {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start
        ) {
            if (isLoading && detailedNews == null) {
                item {
                    LazyLoadingIndicator(paddingValues)
                }
            }

            // 에러 메시지 (데이터가 없을 때만 상단에 표시)
            if (errorMessage != null && detailedNews == null) {
                item {
                    Text(
                        text = "오류: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
            if(detailedNews != null) {
                detailedNews.category?.firstOrNull()?.let { category ->
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

                item {
                    Text(
                        text = detailedNews.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }

                detailedNews.creator?.firstOrNull()?.let { creator ->
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
                item {
                    Spacer(modifier = Modifier.height(32.dp))

                    // 1. 제목은 항상 보여줍니다 (상태와 무관하게)
                    Text(
                        text = "AI 뉴스 요약본",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ColorsLight.primaryColor,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 2. 상태에 따른 분기 처리
                    if (isLoading) {
                        // [로딩 상태] 뺑뺑이와 안내 문구
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomLoadingIndicator()
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "AI가 뉴스를 요약하고 있어요...",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    } else if (errorMessage != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .background(Color(0xFFFFF0F0), shape = RoundedCornerShape(8.dp)) // 연한 빨간 배경
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "요약에 실패했습니다: $errorMessage",
                                fontSize = 14.sp,
                                color = Color.Red
                            )
                        }
                    } else {
                        // [성공 상태] 요약 내용 표시
                        if (detailedNews.description.isNotBlank()) {
                            Text(
                                text = detailedNews.description,
                                fontSize = 18.sp,
                                lineHeight = 26.sp, // 줄간격 추가 (가독성 향상)
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )
                        } else {
                            Text(
                                text = "요약된 내용이 없습니다.",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )
                        }
                    }
                }

                // 5. [즉시 표시] Paging으로 받은 'news' 객체의 데이터 (이미지)
                detailedNews.imageUrl?.let { imageUrl ->
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
                        Text("뉴스 원문 보기",
                            color =  ColorsLight.blueColor,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            modifier = Modifier
                                .clickable{
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detailedNews.link))
                                    context.startActivity(intent)
                                }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = detailedNews.pubDate,
                            fontSize = 14.sp,
                            color = ColorsLight.grayColor,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp)) // 하단 여백 추가
                }
            }
            }
    }
}
