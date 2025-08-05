package com.example.danew.presentation.home
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.danew_app.presentation.home.MainTopAppBar
import com.example.danew_app.presentation.home.MainImageCard
import com.example.danew_app.presentation.home.NewsList
import com.example.danew_app.presentation.home.NowTopNews
import com.example.danew_app.presentation.home.SearchBar

data class Article(
    val title: String,
    val timeAgo: String,
    val thumbnailUrl: String? = null
)


@Composable
fun HomeScreen() {
    val dummyArticles = List(4) {
        Article("오세훈 서울시장 \"대권 도전하겠다\" 13일 공식 출마 선언", "3시간 전", thumbnailUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg")
    }

    Scaffold(
        topBar = { MainTopAppBar() },
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
            NowTopNews("현재 TOP 경제 뉴스", dummyArticles)
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

