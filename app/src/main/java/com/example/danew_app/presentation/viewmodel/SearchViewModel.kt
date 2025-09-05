package com.example.danew_app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.entity.SearchHistoryEntity
import com.example.danew_app.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _recentSearches = MutableStateFlow<List<SearchHistoryEntity>>(emptyList())
    val recentSearches = _recentSearches.asStateFlow()

    init {
        loadRecentSearches()
    }

    fun loadRecentSearches() {
        viewModelScope.launch {
            _recentSearches.value = repository.getRecentSearches()
        }
    }

    fun saveSearch(keyword: String) {
        viewModelScope.launch {
            repository.insertSearch(keyword)
            loadRecentSearches()
        }
    }

    fun deleteSearch(keyword: String) {
        viewModelScope.launch {
            repository.deleteSearch(keyword)
            loadRecentSearches()
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.clearAll()
            loadRecentSearches()
        }
    }
}
