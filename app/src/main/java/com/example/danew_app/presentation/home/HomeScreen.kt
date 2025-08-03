package com.example.danew.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

data class Article(
    val title: String,
    val timeAgo: String,
    val thumbnailUrl: String? = null
)
@Composable
fun CustomTopAppBar() {
    Surface(
        color = Color.White,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // 원하는 높이 설정
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "DANEW",
                    fontWeight = FontWeight.Bold,
                    color = Color.Green,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("검색어를 입력해 주세요.") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "검색") },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun MainImageCard(imageUrl: String, title: String) {
    Column {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun TopImageCard(imageUrl: String, title: String) {
    Column {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}


@Composable
fun NewsList(sectionTitle: String, articles: List<Article>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(sectionTitle, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        articles.forEach {
            NewsItem(it)
        }
    }
}

@Composable
fun NewsRowCardList(sectionTitle:String, articles: List<Article>){
    Column (modifier = Modifier.padding(horizontal = 16.dp)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(sectionTitle, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("전체보기", color = Color.Blue, fontSize = 12.sp)
        }
        articles.get(0).thumbnailUrl?.let { TopImageCard(
            imageUrl = it, title =  articles.get(0).title)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            articles.forEach {
                NewsCard(it)
            }
        }
    }
}

@Composable
fun NewsCard(article:Article) {
    Box(
        modifier = Modifier
            .height(100.dp)
            .width(224.dp)
            .padding(end = 16.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Column (modifier = Modifier.padding(horizontal = 16.dp)){
            Text(article.title, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Text(article.timeAgo, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun NewsItem(article: Article) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(article.title, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(article.timeAgo, color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Warning, contentDescription = "썸네일")
        }
    }
}

@Composable
fun HomeScreen() {
    val dummyArticles = List(8) {
        Article("오세훈 서울시장 \"대권 도전하겠다\" 13일 공식 출마 선언", "3시간 전", thumbnailUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg")
    }

    Scaffold(
        topBar = { CustomTopAppBar() },
    ) {
        padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        )
        {
            SearchBar()
            Spacer(modifier = Modifier.height(36.dp))
            MainImageCard(
                imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
                title = "\"AI 챗봇, 병원 예약·진료 안내 서비스에 도입\""
            )
            Spacer(modifier = Modifier.height(32.dp))
            NewsList("민주님을 위한 추천 뉴스", dummyArticles)
            Spacer(modifier = Modifier.height(16.dp))
            NewsRowCardList("현재 TOP 경제 뉴스", dummyArticles)
            Spacer(modifier = Modifier.height(28.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                dummyArticles.forEach {
                    NewsItem(it)
                }
            }
        }
    }
}

