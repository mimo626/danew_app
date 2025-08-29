package com.example.danew_app.data.mapper

import com.example.danew_app.data.entity.BookmarkEntity
import com.example.danew_app.domain.model.BookmarkModel

fun BookmarkEntity.toDomain(): BookmarkModel {
    return BookmarkModel(
        userId = this.userId,
        articleId = this.articleId,
        bookmarkedAt = this.bookmarkedAt,
    )
}