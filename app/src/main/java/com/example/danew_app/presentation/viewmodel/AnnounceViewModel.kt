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
    var announceList by mutableStateOf<List<AnnounceEntity>>(emptyList())
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
    //     getAnnounceList()
    // }

    // 1. 공지사항 목록 조회
    fun getAnnounceList() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                announceList = announceRepository.getAnnounceList()
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
                // 저장 후 목록 갱신
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
                // 삭제 후 목록 갱신
                getAnnounceList()
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // 성공 상태 초기화 (Snackbar 등을 띄운 후 호출)
    fun resetSuccess() {
        success = false
    }
}