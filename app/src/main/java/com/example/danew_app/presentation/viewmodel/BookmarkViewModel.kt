package com.example.danew_app.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.model.BookmarkModel
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.usecase.CheckBookmarkUseCase
import com.example.danew_app.domain.usecase.DeleteBookmarkUseCase
import com.example.danew_app.domain.usecase.GetBookmarkNewsUseCase
import com.example.danew_app.domain.usecase.GetBookmarksUseCase
import com.example.danew_app.domain.usecase.SaveBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val saveBookmarkUseCase: SaveBookmarkUseCase,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val checkBookmarkUseCase: CheckBookmarkUseCase,
    private val userDataSource: UserDataSource,
) : ViewModel() {

    // [핵심 변경 1] List -> Map<String, NewsModel>로 변경
    // ID를 Key로 사용하여 검색 속도를 O(1)로 만듭니다.
    private val _bookmarkMap = MutableStateFlow<Map<String, NewsModel>>(emptyMap())
    val bookmarkMap: StateFlow<Map<String, NewsModel>> = _bookmarkMap.asStateFlow()

    private val _isBookmark = MutableStateFlow(false)
    val isBookmark: StateFlow<Boolean> = _isBookmark.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun getBookmarks() {
        viewModelScope.launch {
            _isLoading.value = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                val list = getBookmarksUseCase.invoke(token)

                // [핵심 변경 2] 서버에서 받은 List를 Map으로 변환하여 업데이트
                // associateBy: 리스트 아이템의 newsId를 Key로 하는 Map을 생성
                // 기존에 있던 데이터와 비교하여 업데이트하는 효과를 냅니다.
                _bookmarkMap.value = list.associateBy { it.newsId }

            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveBookmark(newsModel: NewsModel) {
        viewModelScope.launch {
            // 저장 로직은 보통 상세화면 등에서 호출되므로 로딩바가 UX를 방해할 수 있어 상황에 따라 조절 필요
            try {
                val token = userDataSource.getToken() ?: ""
                val result = saveBookmarkUseCase.invoke(token, newsModel)

                if (result != null) {
                    _isBookmark.value = true

                    // [핵심 변경 3] 즉시 반영 (Optimistic Update)
                    // 서버 저장 성공 시, 전체 목록을 다시 부르지 않고 로컬 Map에 즉시 추가합니다.
                    _bookmarkMap.update { currentMap ->
                        currentMap + (newsModel.newsId to newsModel)
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            }
        }
    }

    fun deleteBookmark(newsId: String) {
        viewModelScope.launch {
            try {
                val token = userDataSource.getToken() ?: ""
                val isDelete = deleteBookmarkUseCase.invoke(token, newsId)

                if (isDelete) {
                    _isBookmark.value = false

                    // [핵심 변경 4] 즉시 반영
                    // 삭제 성공 시, 로컬 Map에서 해당 ID만 쏙 뺍니다.
                    _bookmarkMap.update { currentMap ->
                        currentMap - newsId
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            }
        }
    }

    fun checkBookmark(newsId: String) {
        // 팁: 굳이 서버 API를 호출하지 않고도, 내 로컬 Map에 있는지 확인하면 더 빠릅니다.
        // 하지만 서버와 동기화가 중요하다면 기존 코드를 유지하세요.
        viewModelScope.launch {
            try {
                val token = userDataSource.getToken() ?: ""
                _isBookmark.value = checkBookmarkUseCase.invoke(token, newsId)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            }
        }
    }
}