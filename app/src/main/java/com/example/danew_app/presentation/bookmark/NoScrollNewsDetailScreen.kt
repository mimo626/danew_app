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
import com.example.danew_app.core.widget.CustomLoadingIndicator
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.core.widget.ShareButton
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.viewmodel.BookmarkViewModel
import com.example.danew_app.presentation.viewmodel.TodayNewsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoScrollNewsDetailScreen(newsId: String, navHostController: NavHostController) {
    val newsViewModel: NewsViewModel = hiltViewModel()
    val news by newsViewModel.newsListById.collectAsState()
    val newsLink = news.link
    val isLoading = newsViewModel.isLoading
    val errorMessage = newsViewModel.errorMessage
    val context = LocalContext.current

    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
    val isBookmarked by bookmarkViewModel.isBookmark.collectAsState()

    val todayNewsViewModel: TodayNewsViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        newsViewModel.fetchNewsById(id = newsId)
        bookmarkViewModel.checkBookmark(newsId)
    }

    LaunchedEffect(news) {
        if (news != NewsModel()) {
            todayNewsViewModel.addNews(news)
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

            // 로딩
            if (isLoading) {
                item {
                    CustomLoadingIndicator(paddingValues)
                }
            }

            // 에러
            if (errorMessage != null) {
                item {
                    Text(
                        text = "오류: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }

            // 뉴스 내용
            if (news != NewsModel()) {
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
                            text = news.pubDate,
                            fontSize = 14.sp,
                            color = ColorsLight.grayColor,
                        )
                    }
                }
            }
        }
    }
}

