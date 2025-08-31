package com.example.danew_app.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.model.BookmarkModel
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.usecase.DeleteBookmarkUseCase
import com.example.danew_app.domain.usecase.GetBookmarksUseCase
import com.example.danew_app.domain.usecase.SaveBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val saveBookmarkUseCase: SaveBookmarkUseCase,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val userDataSource: UserDataSource,
) : ViewModel() {

    var bookmark by mutableStateOf<BookmarkModel?>(null)

    var isDelete by mutableStateOf(false)

    private val _bookmarkedNewsList = MutableStateFlow<List<NewsModel>>(emptyList())
    val bookmarkedNewsList: StateFlow<List<NewsModel>> = _bookmarkedNewsList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun saveBookmark(newsModel: NewsModel) {
        viewModelScope.launch {
            _isLoading.value = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                bookmark = saveBookmarkUseCase.invoke(token, newsModel)
                // 북마크 저장 후 목록 새로 불러오기
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteBookmark(newsId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                isDelete = deleteBookmarkUseCase.invoke(token, newsId)
                // 삭제 후 목록 새로 불러오기
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getBookmarks() {
        viewModelScope.launch {
            _isLoading.value = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                val list = getBookmarksUseCase.invoke(token)
                _bookmarkedNewsList.value = list
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                _isLoading.value = false
            }
        }
    }
}
