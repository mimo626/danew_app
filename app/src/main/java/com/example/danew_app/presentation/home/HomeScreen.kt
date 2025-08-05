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
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.home.MainTopAppBar
import com.example.danew_app.presentation.home.MainImageCard
import com.example.danew_app.presentation.home.NewsList
import com.example.danew_app.presentation.home.NowTopNews
import com.example.danew_app.presentation.home.SearchBar

@Composable
fun HomeScreen() {
    val dummyNewsList = listOf(
        NewsModel(
            id = "1",
            title = "AI 기술, 교육 분야 혁신",
            description = "AI가 교실에 도입되면서 학습 방식이 바뀌고 있습니다.",
            imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
            sourceName = "TechNews",
            pubDate = "2025-08-01",
            category = listOf("Technology", "Education"),
            link = "https://example.com/article1"
        ),
        NewsModel(
            id = "2",
            title = "기후 변화가 미치는 경제 영향",
            description = "지속되는 기후 변화가 산업 전반에 영향을 주고 있습니다.",
            imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
            sourceName = "EcoDaily",
            pubDate = "2025-08-02",
            category = listOf("Environment", "Economy"),
            link = "https://example.com/article2"
        ),
        NewsModel(
            id = "3",
            title = "스마트폰 중독, 청소년 건강 위협",
            description = "과도한 스마트폰 사용이 청소년의 수면과 학습에 영향을 끼칩니다.",
            imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
            sourceName = "HealthLine",
            pubDate = "2025-08-03",
            category = listOf("Health", "Society"),
            link = "https://example.com/article3"
        ),
        NewsModel(
            id = "4",
            title = "우주 탐사 기술, 상용화 눈앞",
            description = "민간 기업들의 참여로 우주 산업이 빠르게 성장하고 있습니다.",
            imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
            sourceName = "SpaceNow",
            pubDate = "2025-08-04",
            category = listOf("Science", "Technology"),
            link = "https://example.com/article4"
        ),
        NewsModel(
            id = "5",
            title = "국내 스타트업, 해외 진출 활발",
            description = "글로벌 시장을 겨냥한 국내 스타트업의 도전이 이어지고 있습니다.",
            imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
            sourceName = "BizReport",
            pubDate = "2025-08-05",
            category = listOf("Business", "Startup"),
            link = "https://example.com/article5"
        ),
        NewsModel(
            id = "6",
            title = "새로운 전기차 배터리 기술 발표",
            description = "더 긴 주행거리와 빠른 충전이 가능한 배터리 기술이 소개되었습니다.",
            imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
            sourceName = "AutoWorld",
            pubDate = "2025-08-06",
            category = listOf("Automobile", "Technology"),
            link = "https://example.com/article6"
        ),
        NewsModel(
            id = "7",
            title = "메타버스 시대, 직업군 변화 예고",
            description = "가상 세계 확장에 따라 새로운 직업들이 등장하고 있습니다.",
            imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
            sourceName = "FutureScope",
            pubDate = "2025-08-07",
            category = listOf("Tech", "Society"),
            link = "https://example.com/article7"
        ),
        NewsModel(
            id = "8",
            title = "한국 전통 문화, 세계에 알린다",
            description = "K-문화 콘텐츠가 세계인들에게 주목받고 있습니다.",
            imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
            sourceName = "CultureToday",
            pubDate = "2025-08-08",
            category = listOf("Culture", "Entertainment"),
            link = "https://example.com/article8"
        )
    )


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
            MainImageCard(dummyNewsList.get(0))
            Spacer(modifier = Modifier.height(32.dp))
            NewsList("민주님을 위한 추천 뉴스", dummyNewsList)
            Spacer(modifier = Modifier.height(16.dp))
            NowTopNews("현재 TOP 경제 뉴스", dummyNewsList)
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

