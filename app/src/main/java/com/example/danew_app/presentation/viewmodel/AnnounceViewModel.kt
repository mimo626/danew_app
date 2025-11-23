package com.example.danew_app.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.dto.AnnounceRequest
import com.example.danew_app.data.entity.AnnounceEntity
import com.example.danew_app.domain.repository.AnnounceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnnounceViewModel @Inject constructor(
    private val announceRepository: AnnounceRepository
) : ViewModel() {

    // 입력 필드 상태
    var title by mutableStateOf("")
    var content by mutableStateOf("")

    // 조회된 공지사항 리스트 상태
    var announceMap by mutableStateOf<Map<String, AnnounceEntity>>(emptyMap())
        private set

    // 로딩, 에러, 성공 상태
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var success by mutableStateOf(false)
        private set

    // 1. 공지사항 목록 조회 및 Map 변환
    fun getAnnounceList() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                // Repository는 여전히 List를 반환한다고 가정합니다.
                val listResult = announceRepository.getAnnounceList()

                // [핵심 변경] List를 Map으로 변환합니다.
                // it.id 또는 it.announceId 부분은 실제 AnnounceEntity의 고유 ID 변수명으로 맞춰주세요.
                announceMap = listResult.associateBy { it.announceId }

            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // 2. 공지사항 저장 (관리자용)
    fun saveAnnounce() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            success = false
            try {
                val request = AnnounceRequest(title = title, content = content)
                announceRepository.saveAnnounce(request)
                success = true

                // 저장 후 목록 갱신 (다시 서버에서 불러와 Map을 재구성)
                getAnnounceList()

                // 입력창 초기화
                content = ""
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // 3. 공지사항 삭제 (관리자용)
    fun deleteAnnounce(announceId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                announceRepository.deleteAnnounce(announceId)

                // [방법 1] 삭제 후 서버에서 다시 목록을 불러와 갱신 (데이터 무결성 좋음)
                getAnnounceList()

                // [방법 2 - 참고용] 서버 호출 없이 로컬 Map에서만 즉시 제거하고 싶다면 아래 코드 사용
                // announceMap = announceMap.toMutableMap().apply { remove(announceId) }

            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // 성공 상태 초기화
    fun resetSuccess() {
        success = false
    }
}