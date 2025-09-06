package com.example.danew_app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.usecase.GetCustomNewsListUseCase
import com.example.danew_app.domain.usecase.GetNewsByCategoryUseCase
import com.example.danew_app.domain.usecase.GetNewsByIdUseCase
import com.example.danew_app.domain.usecase.GetNewsBySearchQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
    private val getNewsByIdUseCase: GetNewsByIdUseCase,
    private val getNewsBySearchQueryUseCase: GetNewsBySearchQueryUseCase,
    private val getCustomNewsListUseCase: GetCustomNewsListUseCase,
    private val userDataSource: UserDataSource,
    ) : ViewModel() {

    var newsListByCategory by mutableStateOf<List<NewsModel>>(emptyList())
        private set

    private val _newsListById = MutableStateFlow<List<NewsModel>>(emptyList())
    val newsListById: StateFlow<List<NewsModel>> = _newsListById

    var newsListBySearchQuery by mutableStateOf<List<NewsModel>>(emptyList())
        private set

    var customNewsMap by mutableStateOf<Map<String, List<NewsModel>>>(emptyMap())
        private set

    // 이미 처리된 데이터
    var mainImageNews by mutableStateOf<NewsModel?>(null)
        private set
    var recommendedNews by mutableStateOf<List<NewsModel>>(emptyList())
        private set
    var keywordNews by mutableStateOf<Map<String, List<NewsModel>>>(emptyMap())
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
                val data = getNewsByIdUseCase(id)
                _newsListById.value = data
                Log.d("NewsViewModel", "newsListById: $data")
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

    fun fetchCustomNews() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                customNewsMap = getCustomNewsListUseCase.invoke(token)
                processNews()
                Log.i("News 맞춤 뉴스 조회", "$customNewsMap")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    private fun processNews() {
        val used = mutableSetOf<String>() // 중복 방지 (url 이나 id 기준)

        // 1) MainImageCard (이미지 있는 첫 번째 뉴스)
        val imageNews = customNewsMap.values
            .flatten()
            .firstOrNull { !it.imageUrl.isNullOrEmpty() }
        mainImageNews = imageNews
        imageNews?.newsId?.let { used.add(it) }

        // 2) 추천 뉴스 (총 8개, 키워드별 균등 분배)
        val keywords = customNewsMap.keys.toList()
        val perKeyword = if (keywords.isEmpty()) 0 else 8 / keywords.size

        val pickedRecommended = mutableListOf<NewsModel>()
        keywords.forEach { keyword ->
            val candidates = customNewsMap[keyword].orEmpty()
                .filter { it.newsId !in used }
                .take(perKeyword)
            pickedRecommended.addAll(candidates)
            candidates.forEach { used.add(it.newsId) }
        }
        recommendedNews = pickedRecommended

        // 3) 키워드별 뉴스 (각 4개씩)
        val perKeywordMap = keywords.associateWith { keyword ->
            customNewsMap[keyword].orEmpty()
                .filter { it.newsId !in used }
                .take(4)
                .also { it.forEach { news -> used.add(news.newsId) } }
        }
        keywordNews = perKeywordMap
    }
}