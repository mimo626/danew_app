package com.example.danew_app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.NewsRepository
import com.example.danew_app.domain.usecase.GetCustomNewsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getCustomNewsListUseCase: GetCustomNewsListUseCase,
    private val userDataSource: UserDataSource,
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val _newsListById = MutableStateFlow(NewsModel())
    val newsListById: StateFlow<NewsModel> = _newsListById

    private val _newsByCategory = MutableStateFlow<PagingData<NewsModel>>(PagingData.empty())
    val newsByCategory: StateFlow<PagingData<NewsModel>> = _newsByCategory

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

    private val searchQueryState = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
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
                newsRepository.getNewsById(id).collect { news ->
                    _newsListById.value = news.toDomain()
                }
                Log.d("NewsViewModel", "newsListById: ${_newsListById.value}")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // 1. userTokenState를 String? 타입으로 복구하고, 초기값을 null로 설정합니다.
    private val userTokenState = MutableStateFlow<String?>(null)

    // 2. 토큰 변경에 반응하는 Paging Flow (로직 변경 없음)
    // token.isNullOrBlank()를 사용하여 null과 "" 모두 처리합니다.
    @OptIn(ExperimentalCoroutinesApi::class)
    val recommendedNewsFlow: Flow<PagingData<NewsModel>> = userTokenState
        .filterNotNull() // null 값은 필터링하여 UseCase 호출 방지
        .flatMapLatest { token ->
            if (token.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                flow {
                    // UseCase 호출
                    emit(getCustomNewsListUseCase(token))
                }.flattenConcat()
            }
        }
        .cachedIn(viewModelScope)


    // 3. 'init' 블록에서 코루틴을 사용하여 토큰을 비동기적으로 로드합니다.
    init {
        loadUserTokenAndStartNewsLoad()
    }

    private fun loadUserTokenAndStartNewsLoad() {
        viewModelScope.launch {
            // suspend 함수인 getToken()을 안전하게 호출합니다.
            val token = userDataSource.getToken() ?: ""

            // 가져온 토큰 값을 userTokenState에 설정하여 Paging Flow를 트리거합니다.
            userTokenState.value = token
        }
    }

    // 외부(예: 로그인/로그아웃)에서 토큰이 변경될 때 수동으로 호출하는 함수.
    fun fetchRecommendedNews(token: String) {
        userTokenState.value = token
    }
}