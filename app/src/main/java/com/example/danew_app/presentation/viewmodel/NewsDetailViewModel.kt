package com.example.danew_app.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.entity.NewsDetailType
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.model.UserModel
import com.example.danew_app.domain.repository.NewsDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsDetailRepository: NewsDetailRepository
) : ViewModel() {

    private val _newsDetail = MutableStateFlow(NewsModel())
    var newsDetail: StateFlow<NewsModel> = _newsDetail

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchNewsDetail(newsId: String, type: NewsDetailType) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                _newsDetail.value = newsDetailRepository.getNewsDetail(newsId, type)
            }catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}