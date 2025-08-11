package com.example.danew_app.presentation.category

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.usecase.GetNewsByCategoryUseCase
import com.example.danew_app.domain.usecase.GetNewsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
    private val getNewsByIdUseCase: GetNewsByIdUseCase
) : ViewModel() {

    var newsList by mutableStateOf<List<NewsModel>>(emptyList())
        private set

    private val _newsListById = MutableStateFlow<List<NewsModel>>(emptyList())
    val newsListById: StateFlow<List<NewsModel>> = _newsListById

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchNewsByCategory(category: String, loadMore: Boolean = false) {
        viewModelScope.launch {
            if (!loadMore) {
                isLoading = true
                newsList = emptyList() // 새로 탭을 눌렀을 때 기존 데이터 초기화
            }

            errorMessage = null
            try {
                val newList = getNewsByCategoryUseCase.invoke(category, loadMore)
                newsList = if (loadMore) newsList + newList else newList
                Log.d("NewsViewModel", "newsListByCategory: ${loadMore}")
                Log.d("NewsViewModel", "newsListByCategory: ${newsList.size}")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                if (!loadMore) isLoading = false
            }
        }
    }


    fun fetchNewsById(id: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                viewModelScope.launch {
                    val data = getNewsByIdUseCase(id)
                    _newsListById.value = data
                    Log.d("NewsViewModel", "newsListById: $data")
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

}

