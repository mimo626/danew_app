package com.example.danew_app.data.mapper

import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.domain.model.NewsModel

fun NewsEntity.toDomain(): NewsModel {
    return NewsModel(
        id = this.articleId ?: "",
        title = this.title ?: "제목 없음",
        description = this.description ?: "설명이 없습니다.",
        imageUrl = this.imageUrl,
        sourceName = this.sourceName ?: "알 수 없음",
        pubDate = this.pubDate,
        category = this.category,
        link = this.link
    )
}