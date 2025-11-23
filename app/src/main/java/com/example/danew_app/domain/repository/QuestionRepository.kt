package com.example.danew_app.domain.repository

import com.example.danew_app.data.dto.QuestionRequest
import com.example.danew_app.data.entity.QuestionEntity

interface QuestionRepository {
    suspend fun saveQuestion(questionRequest: QuestionRequest): QuestionEntity
    suspend fun getQuestionList(): List<QuestionEntity>
    suspend fun deleteQuestion(questionId: String): String
}