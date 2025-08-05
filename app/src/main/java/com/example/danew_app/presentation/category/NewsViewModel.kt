package com.example.danew_app.presentation.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.remote.RetrofitInstance
import com.example.danew_app.domain.model.NewsModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue

class NewsViewModel : ViewModel() {
    var newsList by mutableStateOf<List<NewsModel>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchNewsByCategory(category: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = RetrofitInstance.newsApi.fetchNewsByCategory(category = category)
                newsList = response.results
                println("카테고리별 뉴스 가져오기 성공: ${newsList}")
            } catch (e: Exception) {
                errorMessage = "카테고리별 뉴스 가져오기 실패: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }
}
