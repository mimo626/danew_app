package com.example.danew_app.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.entity.TodayNewsEntity
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.domain.repository.TodayNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TodayNewsViewModel @Inject constructor(
    private val repository: TodayNewsRepository,
    private val userDataSource: UserDataSource,
    ) : ViewModel() {

    private val _todayNewsList = MutableStateFlow<List<TodayNewsEntity>>(emptyList())
    val todayNewsList = _todayNewsList.asStateFlow()

    private val _todayNews = MutableStateFlow<TodayNewsEntity?>(null)
    val todayNews = _todayNews.asStateFlow()

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        refreshTodayNews()
    }

    private fun refreshTodayNews() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val userToken = userDataSource.getToken() ?: ""
                repository.clearOldNews() // ✅ 날짜 바뀌면 이전 데이터 삭제
                _todayNewsList.value = repository.getTodayNewsList(userId = userToken)
            }catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun addNews(newsModel: NewsModel) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                if(newsModel.newsId != ""){
                    val userToken = userDataSource.getToken() ?: ""
                    repository.addNews(userId = userToken, newsModel = newsModel)
                }
            }catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun getNewsList() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val userToken = userDataSource.getToken() ?: ""
                _todayNewsList.value = repository.getTodayNewsList(userId = userToken)
                Log.d("Today News 리스트 조회", "${_todayNewsList.value}")
            }catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun getNews(newsId:String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val userToken = userDataSource.getToken() ?: ""
                _todayNews.value = repository.getTodayNews(userId = userToken, newsId = newsId)
                Log.d("Today News 조회", "${_todayNews.value}")
            }catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val userToken = userDataSource.getToken() ?: ""
                repository.clearAll(userId = userToken)
            }catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}
