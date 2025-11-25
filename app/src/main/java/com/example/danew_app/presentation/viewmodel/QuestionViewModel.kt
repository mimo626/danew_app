package com.example.danew_app.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.dto.QuestionRequest
import com.example.danew_app.data.entity.QuestionEntity
import com.example.danew_app.domain.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    // 입력 필드 상태
    var questionInput by mutableStateOf("")
    var answerInput by mutableStateOf("")

    // 조회된 FAQ 리스트 상태
    var questionList by mutableStateOf<List<QuestionEntity>>(emptyList())
        private set

    // 로딩, 에러, 성공 상태
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var success by mutableStateOf(false)
        private set

    // 초기화 시 리스트 불러오기 (필요하다면 주석 해제)
    // init {
    //     getQuestionList()
    // }

    // 1. FAQ 목록 조회
    fun getQuestionList() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                questionList = questionRepository.getQuestionList()
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // 2. FAQ 저장 (관리자용)
    fun saveQuestion() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            success = false
            try {
                val request = QuestionRequest(
                    question = questionInput,
                    answer = answerInput
                )
                questionRepository.saveQuestion(request)
                success = true

                // 저장 후 목록 갱신 및 입력 초기화
                getQuestionList()
                questionInput = ""
                answerInput = ""
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // 3. FAQ 삭제 (관리자용)
    fun deleteQuestion(questionId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                questionRepository.deleteQuestion(questionId)
                // 삭제 후 목록 갱신
                getQuestionList()
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun resetSuccess() {
        success = false
    }
}