package com.example.danew_app.data.mapper

import com.example.danew_app.data.entity.BookmarkEntity
import com.example.danew_app.domain.model.BookmarkModel

fun BookmarkEntity.toDomain(): BookmarkModel {
    return BookmarkModel(
        articleId = this.id.articleId,
        bookmarkedAt = this.bookmarkedAt,
    )
}