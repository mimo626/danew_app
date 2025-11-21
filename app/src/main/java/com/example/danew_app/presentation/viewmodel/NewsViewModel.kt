package com.example.danew_app.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getCustomNewsListUseCase: GetCustomNewsListUseCase,
    private val userDataSource: UserDataSource,
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val _newsMap = MutableStateFlow<Map<String, NewsModel>>(emptyMap())
    val newsMap: StateFlow<Map<String, NewsModel>> = _newsMap.asStateFlow()

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val _selectedCategory = MutableStateFlow<String?>(null)

    val newsByCategoryFlow: Flow<PagingData<NewsModel>> = _selectedCategory
        .filterNotNull() // null이면 실행 안 함
        .flatMapLatest { category ->
            // Repository에서 Flow<PagingData>를 바로 리턴받아 연결
            newsRepository.getNewsByCategory(category)
                .map { pagingData ->
                    pagingData.map { it.toDomain() }
                }
        }
        .cachedIn(viewModelScope) // ⭐️ 중요: 여기서 캐싱을 해야 상세화면 갔다와도 데이터가 유지됨

    fun fetchNewsByCategory(category: String) {
        // 이미 같은 카테고리면 다시 로드하지 않도록 방어 로직 추가 가능
        if (_selectedCategory.value != category) {
            _selectedCategory.value = category
        }
    }

    fun fetchNewsById(id: String) {
        // [핵심 1] 캐싱: 이미 Map에 해당 ID의 데이터가 있다면 API를 호출하지 않고 종료합니다.
        if (_newsMap.value.containsKey(id)) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                newsRepository.getNewsById(id).collect { news ->
                    val domainNews = news.toDomain()
                    Log.d("News 가져오기: ", "${domainNews}")

                    // [핵심 2] Map 업데이트
                    // 기존 Map에 새로운 (id -> news) 쌍을 추가하여 업데이트합니다.
                    _newsMap.update { currentMap ->
                        currentMap + (id to domainNews)
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                Log.e("News 가져오기 실패", "${e}, ${_newsMap.value}")

            } finally {
                isLoading = false
            }
        }
    }

    // 1. userTokenState를 String? 타입으로 복구하고, 초기값을 null로 설정합니다.
    private val userTokenState = MutableStateFlow<String?>(null)

    // 2. 토큰 변경에 반응하는 Paging Flow (로직 변경 없음)
    // token.isNullOrBlank()를 사용하여 null과 "" 모두 처리합니다.
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


    fun fetchNewsBySearchQuery(query: String) {
        // 1. Compose에서 이 함수를 호출하여 검색어 상태를 업데이트
        searchQueryState.value = query

        // 2. searchQueryState가 업데이트되면 flatMapLatest가 트리거되어
        //    위에서 새로운 Pager Flow를 만들고, PagingSource의 load()가 호출됩니다.
    }

    // 외부(예: 로그인/로그아웃)에서 토큰이 변경될 때 수동으로 호출하는 함수.
    fun fetchRecommendedNews(token: String) {
        userTokenState.value = token
    }
}