package com.example.danew_app.data.mapper

import com.example.danew_app.data.entity.DiaryEntity
import com.example.danew_app.domain.model.DiaryModel

fun DiaryEntity.toDomain(): DiaryModel? {
    if (diaryId == null && userId == null && createdAt == null && content == null) return null
    return DiaryModel(
        diaryId = this.diaryId,
        createdAt = this.createdAt,
        content = this.content,
    )
}