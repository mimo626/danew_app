package com.example.danew_app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.model.BookmarkModel
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.usecase.GetBookmarksUseCase
import com.example.danew_app.domain.usecase.SaveBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val saveBookmarkUseCase: SaveBookmarkUseCase,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val userDataSource: UserDataSource,
) : ViewModel(){

    var bookmark by mutableStateOf<BookmarkModel?>(null)

    var bookmarkedNewsList by mutableStateOf<List<NewsModel>>(emptyList())

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun saveBookmark(newsModel: NewsModel){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                bookmark = saveBookmarkUseCase.invoke(token, newsModel)
            }catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading =false
            }
        }
    }

    fun getBookmarks() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                bookmarkedNewsList = getBookmarksUseCase.invoke(token)
            }catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading =false
            }
        }
    }
}