package com.example.danew_app.domain.usecase

import android.util.Log
import com.example.danew_app.data.entity.DiaryEntity
import com.example.danew_app.domain.repository.DiaryRepository
import javax.inject.Inject

class SaveDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(content:String, createdAt: String, token:String):DiaryEntity{
        return diaryRepository.saveDiary(content, createdAt, token)
    }
}