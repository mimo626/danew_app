package com.example.danew_app.domain.repository

import com.example.danew_app.data.dto.DiaryRequest
import com.example.danew_app.data.entity.DiaryEntity

interface DiaryRepository {
    suspend fun saveDiary(content:String, createdAt: String, token:String):DiaryEntity
    suspend fun getByDate(token: String, createdAt:String):DiaryEntity?
    suspend fun updateDiary(token:String, diaryId:String, diaryRequest: DiaryRequest): DiaryEntity
}