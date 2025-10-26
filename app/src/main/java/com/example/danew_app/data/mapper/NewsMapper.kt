package com.example.danew_app.data.mapper

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.danew_app.data.entity.MetaNewsEntity
import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.data.entity.TodayNewsEntity
import com.example.danew_app.domain.model.NewsModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration

@RequiresApi(Build.VERSION_CODES.O)
fun NewsEntity.toDomain(): NewsModel {
    return NewsModel(
        newsId = this.article_id ?: "",
        title = this.title ?: "제목 없음",
        description = this.description ?: "설명이 없습니다.",
        imageUrl = this.image_url,
        sourceName = this.source_name ?: "알 수 없음",
        pubDate = formatTimeAgo(this.pubDate),
        category = getCategoryToKr(this.category),
        link = this.link,
        creator = this.creator,
        language = this.language,
        keywords = this.keywords,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun MetaNewsEntity.toDomain(): NewsModel {
    return NewsModel(
        newsId = this.id,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        sourceName = this.sourceName,
        pubDate = formatTimeAgo(this.pubDate),
        category = getCategoryToKr(this.category),
        link = this.link,
        creator = this.creator,
        language = this.language,
        keywords = this.keywords,
    )
}

fun NewsEntity.toMeta():MetaNewsEntity{
    return MetaNewsEntity(
        id = this.article_id ?: "",
        title = this.title ?: "제목 없음",
        description = this.content ?: "설명이 없습니다.",
        imageUrl = this.image_url,
        sourceName = this.source_name ?: "알 수 없음",
        pubDate = this.pubDate,
        category = this.category,
        link = this.link,
        creator = this.creator,
        language = this.language,
        keywords = this.keywords,
        savedAt = ""
    )
}

fun NewsModel.toMeta():MetaNewsEntity{
    return MetaNewsEntity(
        id = this.newsId,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        sourceName = this.sourceName,
        pubDate = this.pubDate,
        category = getCategoryToEn(this.category),
        link = this.link,
        creator = this.creator,
        language = this.language,
        keywords = this.keywords,
        savedAt = ""
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun NewsModel.toTodayNews():TodayNewsEntity{
    return TodayNewsEntity(
        newsId = this.newsId,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        sourceName = this.sourceName,
        pubDate = this.pubDate,
        category = this.category,
        link = this.link,
        creator = this.creator,
        language = this.language,
        keywords = this.keywords,
        savedAt = LocalDate.now().toString()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun TodayNewsEntity.toDomain():NewsModel{
    return NewsModel(
        newsId = this.newsId,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        sourceName = this.sourceName,
        pubDate = this.pubDate,
        category = this.category,
        link = this.link,
        creator = this.creator,
        language = this.language,
        keywords = this.keywords,
    )
}

fun getCategoryToKr(enCategory:List<String>?):List<String>?{
    val krCategory = enCategory?.map {
        makeCategoryToKr(it)
    }
    return krCategory
}

fun getCategoryToEn(krCategory: List<String>?): List<String>? {
    val enCategory = krCategory?.map {
        makeCategoryToEn(it)
    }
    return enCategory
}

fun makeCategoryToKr(category: String):String {
    return when (category) {
        "business" -> "경제"
        "entertainment" -> "엔터"
        "environment" -> "환경"
        "food" -> "음식"
        "health" -> "건강"
        "politics" -> "정치"
        "science" -> "과학"
        "sports" -> "스포츠"
        "technology" -> "기술"
        "top" -> "인기"
        "world" -> "세계"
        "lifestyle" -> "일상"
        "other" -> "기타"

        else -> category
    }
}

fun makeCategoryToEn(category: String): String {
    return when (category) {
        "경제" -> "business"
        "엔터" -> "entertainment"
        "환경" -> "environment"
        "음식" -> "food"
        "건강" -> "health"
        "정치" -> "politics"
        "과학" -> "science"
        "스포츠" -> "sports"
        "기술" -> "technology"
        "인기" -> "top"
        "세계" -> "world"
        "일상" -> "lifestyle"
        "기타" -> "other"

        else -> category
    }
}

/**
 * "yyyy-MM-dd-HH:mm:ss" 형식의 날짜 문자열을 받아
 * 현재 시간과 비교하여 "n시간 전", "n일 전" 등으로 변환합니다.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun formatTimeAgo(dateString: String?): String {
    if (dateString.isNullOrBlank()) {
        return "시간 정보 없음"
    }

    return try {
        // 1. 입력 형식에 맞는 DateTimeFormatter 생성
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        // 2. 날짜 문자열을 LocalDateTime 객체로 파싱
        val pastTime = LocalDateTime.parse(dateString, formatter)

        // 3. 현재 시간 가져오기
        val now = LocalDateTime.now()

        // 4. 두 시간 사이의 간격(Duration) 계산
        val duration = Duration.between(pastTime, now)

        // 5. 시간 단위로 변환
        val hours = duration.toHours()

        if (hours < 24) {
            // 24시간 미만인 경우
            when {
                hours < 1 -> "방금 전" // 1시간 미만은 "방금 전"으로 처리
                else -> "${hours}시간 전"
            }
        } else {
            // 24시간 이상인 경우
            val days = duration.toDays()
            "${days}일 전"
        }

    } catch (e: Exception) {
        // 파싱 실패 등 예외 발생 시 원본 문자열 반환
        Log.e("News 게시날짜 변환", "오류 ${e}",)
        dateString
    }
}