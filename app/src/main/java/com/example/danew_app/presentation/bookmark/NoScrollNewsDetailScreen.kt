package com.example.danew_app.presentation.bookmark

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
import com.example.danew_app.core.widget.LazyLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.core.widget.ShareButton
import com.example.danew_app.data.entity.NewsDetailType
import com.example.danew_app.presentation.viewmodel.BookmarkViewModel
import com.example.danew_app.presentation.viewmodel.NewsDetailViewModel
import com.example.danew_app.presentation.viewmodel.TodayNewsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoScrollNewsDetailScreen(
    newsId: String,
    listType: NewsDetailType,
    navHostController: NavHostController,
    newsDetailViewModel: NewsDetailViewModel = hiltViewModel(),
    ) {
    val context = LocalContext.current

    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
    val todayNewsViewModel: TodayNewsViewModel = hiltViewModel()

    val isBookmarked by bookmarkViewModel.isBookmark.collectAsState()
    val detailedNews by newsDetailViewModel.newsDetail.collectAsState()
    val isLoading = newsDetailViewModel.isLoading
    val errorMessage = newsDetailViewModel.errorMessage

    // 4. 데이터 요청 로직 분기 (API 호출 vs 로컬 조회)
    LaunchedEffect(newsId, listType) {
        bookmarkViewModel.checkBookmark(newsId)
        newsDetailViewModel.fetchNewsDetail(newsId, listType)

    }
    LaunchedEffect(detailedNews) {
        todayNewsViewModel.addNews(detailedNews)
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
            if (isLoading) {
                item {
                    LazyLoadingIndicator(paddingValues)
                }
            }
            if (errorMessage != null) {
                item {
                    Text(
                        text = "오류: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
            if (detailedNews != null) {
                // 카테고리 태그
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

                // 뉴스 제목
                item {
                    Text(
                        text = detailedNews.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }

                // 작성자
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
                    Text(detailedNews.description, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 20.dp))
                }

                // 이미지
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
                        // 날짜
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = detailedNews.pubDate,
                            fontSize = 14.sp,
                            color = ColorsLight.grayColor,
                        )
                    }
                }
            }
        }
    }
}

