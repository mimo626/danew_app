package com.example.danew_app.domain.usecase

import com.example.danew_app.data.entity.DiaryEntity
import com.example.danew_app.domain.repository.DiaryRepository
import javax.inject.Inject

class SaveDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(content:String, token:String):DiaryEntity{
        return diaryRepository.saveDiary(content, token)
    }
}