package com.example.danew_app.domain.usecase

import com.example.danew_app.data.dto.DiaryRequest
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.DiaryModel
import com.example.danew_app.domain.repository.DiaryRepository
import javax.inject.Inject

class UpdateDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
){
    suspend operator fun invoke(diaryId:String, diaryRequest: DiaryRequest, token:String): DiaryModel? {
        return diaryRepository.updateDiary(token, diaryId, diaryRequest).toDomain()
    }
}