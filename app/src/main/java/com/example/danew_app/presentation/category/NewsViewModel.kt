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
import com.example.danew_app.domain.usecase.GetNewsBySearchQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
    private val getNewsByIdUseCase: GetNewsByIdUseCase,
    private val getNewsBySearchQueryUseCase: GetNewsBySearchQueryUseCase
) : ViewModel() {

    var newsListByCategory by mutableStateOf<List<NewsModel>>(emptyList())
        private set

    private val _newsListById = MutableStateFlow<List<NewsModel>>(emptyList())
    val newsListById: StateFlow<List<NewsModel>> = _newsListById

    var newsListBySearchQuery by mutableStateOf<List<NewsModel>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchNewsByCategory(category: String, loadMore: Boolean = false) {
        viewModelScope.launch {
            if (!loadMore) {
                isLoading = true
                newsListByCategory = emptyList() // 새로 탭을 눌렀을 때 기존 데이터 초기화
            }

            errorMessage = null
            try {
                val newList = getNewsByCategoryUseCase.invoke(category, loadMore)
                newsListByCategory = if (loadMore) newsListByCategory + newList else newList
                Log.d("NewsViewModel", "loadMore: ${loadMore}")
                Log.d("NewsViewModel", "newsList.size: ${newsListByCategory.size}")
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

    fun fetchNewsBySearchQuery(searchQuery: String, loadMore: Boolean = false) {
        viewModelScope.launch {
            if (!loadMore) {
                isLoading = true
                newsListBySearchQuery = emptyList() // 새로 탭을 눌렀을 때 기존 데이터 초기화
            }

            errorMessage = null
            try {
                val newList = getNewsBySearchQueryUseCase.invoke(searchQuery, loadMore)
                newsListBySearchQuery = if (loadMore) newsListBySearchQuery + newList else newList
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                if (!loadMore) isLoading = false
            }
        }
    }

    fun resetSearchResults() {
        newsListBySearchQuery = emptyList()
    }


}

