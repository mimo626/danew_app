package com.example.danew_app.domain.usecase

import android.util.Log
import com.example.danew_app.data.entity.DiaryEntity
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.DiaryModel
import com.example.danew_app.domain.repository.DiaryRepository
import javax.inject.Inject

class DiaryGetByDateUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
){
    suspend operator fun invoke(token:String, createdAt:String) : DiaryModel? {
        return diaryRepository.getByDate(token, createdAt)?.toDomain()
    }
}