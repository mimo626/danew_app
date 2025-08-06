package com.example.danew_app.core.gloabals

import com.example.danew_app.domain.model.NewsModel
import kotlinx.datetime.LocalDateTime

class Globals {
    companion object {
        val dummyNewsList = listOf(
            NewsModel(
                id = "1",
                title = "AI 기술, 교육 분야 혁신",
                description = "AI가 교실에 도입되면서 학습 방식이 바뀌고 있습니다.",
                imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
                sourceName = "TechNews",
                pubDate = "2025-08-01",
                category = listOf("Technology", "Education"),
                link = "https://example.com/article1",
                creator = listOf("강민주 기자")
            ),
            NewsModel(
                id = "2",
                title = "기후 변화가 미치는 경제 영향",
                description = "지속되는 기후 변화가 산업 전반에 영향을 주고 있습니다.",
                imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
                sourceName = "EcoDaily",
                pubDate = "2025-08-02",
                category = listOf("Environment", "Economy"),
                link = "https://example.com/article2",
                creator = listOf("강민주 기자")
            ),
            NewsModel(
                id = "3",
                title = "스마트폰 중독, 청소년 건강 위협",
                description = "과도한 스마트폰 사용이 청소년의 수면과 학습에 영향을 끼칩니다.",
                imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
                sourceName = "HealthLine",
                pubDate = "2025-08-03",
                category = listOf("Health", "Society"),
                link = "https://example.com/article3",
                creator = listOf("강민주 기자")
            ),
            NewsModel(
                id = "4",
                title = "우주 탐사 기술, 상용화 눈앞",
                description = "민간 기업들의 참여로 우주 산업이 빠르게 성장하고 있습니다.",
                imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
                sourceName = "SpaceNow",
                pubDate = "2025-08-04",
                category = listOf("Science", "Technology"),
                link = "https://example.com/article4",
                creator = listOf("강민주 기자")
            ),
            NewsModel(
                id = "5",
                title = "국내 스타트업, 해외 진출 활발",
                description = "글로벌 시장을 겨냥한 국내 스타트업의 도전이 이어지고 있습니다.",
                imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
                sourceName = "BizReport",
                pubDate = "2025-08-05",
                category = listOf("Business", "Startup"),
                link = "https://example.com/article5",
                creator = listOf("강민주 기자")

            ),
            NewsModel(
                id = "6",
                title = "새로운 전기차 배터리 기술 발표",
                description = "더 긴 주행거리와 빠른 충전이 가능한 배터리 기술이 소개되었습니다.",
                imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
                sourceName = "AutoWorld",
                pubDate = "2025-08-06",
                category = listOf("Automobile", "Technology"),
                link = "https://example.com/article6",
                creator = listOf("강민주 기자")

            ),
            NewsModel(
                id = "7",
                title = "메타버스 시대, 직업군 변화 예고",
                description = "가상 세계 확장에 따라 새로운 직업들이 등장하고 있습니다.",
                imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
                sourceName = "FutureScope",
                pubDate = "2025-08-07",
                category = listOf("Tech", "Society"),
                link = "https://example.com/article7",
                creator = listOf("강민주 기자")

            ),
            NewsModel(
                id = "8",
                title = "한국 전통 문화, 세계에 알린다",
                description = "K-문화 콘텐츠가 세계인들에게 주목받고 있습니다.",
                imageUrl = "https://file2.nocutnews.co.kr/newsroom/image/2025/07/30/202507300112391701_0.jpg",
                sourceName = "CultureToday",
                pubDate = "2025-08-08",
                category = listOf("Culture", "Entertainment"),
                link = "https://example.com/article8",
                creator = listOf("강민주 기자")
            )
        )
    }
}