package com.example.danew_app.domain.usecase

import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.DiaryModel
import com.example.danew_app.domain.repository.DiaryRepository
import javax.inject.Inject

class SaveDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(content:String, createdAt: String, token:String): DiaryModel? {
        return diaryRepository.saveDiary(content, createdAt, token).toDomain()
    }
}