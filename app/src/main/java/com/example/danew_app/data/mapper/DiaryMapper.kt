package com.example.danew_app.data.mapper

import com.example.danew_app.data.entity.DiaryEntity
import com.example.danew_app.domain.model.DiaryModel

fun DiaryEntity.toDomain(): DiaryModel {
    return DiaryModel(
        diaryId = this.diaryId,
        createdAt = this.createdAt,
        content = this.content,
    )
}