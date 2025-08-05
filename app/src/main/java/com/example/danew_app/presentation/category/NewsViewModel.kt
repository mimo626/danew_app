package com.example.danew_app.presentation.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.remote.RetrofitInstance
import com.example.danew_app.data.entity.NewsEntity
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.usecase.GetNewsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase
) : ViewModel() {

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
                newsList = getNewsByCategoryUseCase(category)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}

