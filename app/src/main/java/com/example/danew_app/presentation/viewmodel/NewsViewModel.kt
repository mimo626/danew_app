package com.example.danew_app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.data.remote.NewsApi
import com.example.danew_app.data.repository.SearchNewsPagingSource
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import com.example.danew_app.domain.usecase.GetNewsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsByIdUseCase: GetNewsByIdUseCase,
    private val userDataSource: UserDataSource,
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val _newsListById = MutableStateFlow<List<NewsModel>>(emptyList())
    val newsListById: StateFlow<List<NewsModel>> = _newsListById

    var customNewsMap by mutableStateOf<Map<String, List<NewsModel>>>(emptyMap())
        private set

    private val _newsByCategory = MutableStateFlow<PagingData<NewsModel>>(PagingData.empty())
    val newsByCategory: StateFlow<PagingData<NewsModel>> = _newsByCategory

    private val searchQueryState = MutableStateFlow("")
    val newsBySearchQuery = searchQueryState
        // 검색어가 변경될 때마다 새로운 Pager Flow를 생성
        .flatMapLatest { query ->
            if (query.isBlank()) {
                // 빈 검색어일 경우 빈 PagingData 반환
                flowOf(PagingData.empty())
            } else {
                // 검색어가 있을 경우 Pager 구성 및 Flow 방출
                newsRepository.getNewsBySearchQuery(searchQuery = query)
            }
        }
        .cachedIn(viewModelScope) // Flow를 캐시하여 화면 회전 등에 대응

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

    fun fetchNewsByCategory(category: String) {
        viewModelScope.launch {
            try {
                errorMessage = null
                Log.d("NewsViewModel", "카테고리 뉴스 조회 스크롤1")

                newsRepository.getNewsByCategory(category) // Flow<PagingData<NewsEntity>>
                    .map { pagingData ->
                        pagingData.map { it.toDomain() } // PagingData.map
                    }
                    .cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        _newsByCategory.value = pagingData  // PagingData 직접 저장
                    }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
            }
        }
    }
    // NewsViewModel.kt (예시)

    fun fetchNewsBySearchQuery(query: String) {
        // 1. Compose에서 이 함수를 호출하여 검색어 상태를 업데이트
        searchQueryState.value = query

        // 2. searchQueryState가 업데이트되면 flatMapLatest가 트리거되어
        //    위에서 새로운 Pager Flow를 만들고, PagingSource의 load()가 호출됩니다.
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

    fun fetchCustomNews() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
//                customNewsMap = getCustomNewsListUseCase.invoke(token)
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