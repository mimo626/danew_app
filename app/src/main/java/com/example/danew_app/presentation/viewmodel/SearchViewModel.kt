package com.example.danew_app.presentation.viewmodel
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.entity.SearchHistoryEntity
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val userDataSource: UserDataSource,
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
                val userToken = userDataSource.getToken() ?: ""
                _recentSearches.value = repository.getRecentSearches(userToken)
            } catch (e:Exception){
                Log.e("DB 오류", "최근 검색어 조회 실패", e)
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
                val userToken = userDataSource.getToken() ?: ""
                val searchHistoryEntity = SearchHistoryEntity(userId = userToken, keyword = keyword)
                repository.insertSearch(searchHistoryEntity)
                loadRecentSearches()
            } catch (e:Exception){
                Log.e("DB 오류", "최근 검색어 저장 실패", e)
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
                val userToken = userDataSource.getToken() ?: ""
                repository.deleteSearch(userId = userToken, keyword = keyword)
                loadRecentSearches()
            } catch (e:Exception){
                Log.e("DB 오류", "최근 검색어 삭제 실패", e)
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
                loadRecentSearches()
            } catch (e:Exception){
                Log.e("DB 오류", "최근 검색어 전체 삭제 실패", e)
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}
