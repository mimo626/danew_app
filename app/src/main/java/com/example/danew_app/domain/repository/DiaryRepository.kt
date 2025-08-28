package com.example.danew_app.domain.repository

import com.example.danew_app.data.entity.DiaryEntity

interface DiaryRepository {
    suspend fun saveDiary(content:String, token:String):DiaryEntity
    suspend fun getByDate(token: String, createdAt:String):DiaryEntity
}