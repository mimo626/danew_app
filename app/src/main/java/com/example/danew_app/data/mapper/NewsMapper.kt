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
        description = this.description ?: "설명이 없습니다.",
        imageUrl = this.image_url,
        sourceName = this.source_name ?: "알 수 없음",
        pubDate = this.pubDate,
        category = this.category,
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

fun NewsEntity.toMeta():MetaNewsEntity{
    return MetaNewsEntity(
        id = this.article_id ?: "",
        title = this.title ?: "제목 없음",
        description = this.description ?: "설명이 없습니다.",
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
        category = this.category,
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