package com.example.danew_app.presentation.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    init {
        loadRecentSearches()
    }

    fun loadRecentSearches() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                _recentSearches.value = repository.getRecentSearches()
            } catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun saveSearch(keyword: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                repository.insertSearch(keyword)
                loadRecentSearches()
            } catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }

        }
    }

    fun deleteSearch(keyword: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                repository.deleteSearch(keyword)
                loadRecentSearches()
            } catch (e:Exception){
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
                repository.clearAll()
                loadRecentSearches()
            } catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}
