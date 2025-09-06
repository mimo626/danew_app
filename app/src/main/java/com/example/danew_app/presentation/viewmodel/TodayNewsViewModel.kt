package com.example.danew_app.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.entity.TodayNewsEntity
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
    private val repository: TodayNewsRepository
) : ViewModel() {

    private val _todayNews = MutableStateFlow<List<TodayNewsEntity>>(emptyList())
    val todayNews = _todayNews.asStateFlow()

    init {
        refreshTodayNews()
    }

    private fun refreshTodayNews() {
        viewModelScope.launch {
            repository.clearOldNews() // ✅ 날짜 바뀌면 이전 데이터 삭제
            _todayNews.value = repository.getTodayNews()
        }
    }

    fun addNews(newsModel: NewsModel) {
        viewModelScope.launch {
            repository.addNews(newsModel)
        }
    }

    fun getNews() {
        viewModelScope.launch {
            _todayNews.value = repository.getTodayNews()
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.clearAll()
            _todayNews.value = emptyList()
        }
    }
}
