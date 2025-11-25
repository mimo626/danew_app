package com.example.danew_app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.dto.AnnounceRequest
import com.example.danew_app.data.entity.AnnounceEntity
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.repository.AnnounceRepository
import com.example.danew_app.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val userDataSource: UserDataSource,
    ) : ViewModel() {

    // 로딩, 에러, 성공 상태
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var success by mutableStateOf(false)
        private set

    fun sendPush() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                val result = notificationRepository.sendNotification("fhfA4qcbQZ-mtKO5QnQwYF:APA91bGPB3lnH_vJ6LtWJtZX6iV-55ptFQ34pae3U1Zm3fWk3Rc_fO3M53LlJrIR0WHOblExPCN1jGRK_ujf-y6dFQZNN_IWYsTNhZF8WXtEiJNNGL9G5n4", "안드로이드유저")
                Log.d("Notification 성공", "결과: $result")
            } catch (e: Exception) {
                Log.e("Notification 실패", "에러 발생: ${e.message}")
            }finally {
                isLoading = false
            }
        }
    }
}