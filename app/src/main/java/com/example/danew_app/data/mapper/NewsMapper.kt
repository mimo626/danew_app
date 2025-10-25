package com.example.danew_app.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.danew_app.data.entity.MetaNewsEntity
import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.data.entity.TodayNewsEntity
import com.example.danew_app.domain.model.NewsModel
import java.time.LocalDate

fun NewsEntity.toDomain(): NewsModel {
    return NewsModel(
        newsId = this.article_id ?: "",
        title = this.title ?: "제목 없음",
        content = this.description ?: "설명이 없습니다.",
        imageUrl = this.image_url,
        sourceName = this.source_name ?: "알 수 없음",
        pubDate = this.pubDate,
        category = getCategoryToKr(this.category),
        link = this.link,
        creator = this.creator,
        language = this.language,
        keywords = this.keywords,
    )
}

fun MetaNewsEntity.toDomain(): NewsModel {
    return NewsModel(
        newsId = this.id,
        title = this.title,
        content = this.description,
        imageUrl = this.imageUrl,
        sourceName = this.sourceName,
        pubDate = this.pubDate,
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
        description = this.content,
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
        description = this.content,
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
        content = this.description,
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
        "기타" -> "other"

        else -> category
    }
}
