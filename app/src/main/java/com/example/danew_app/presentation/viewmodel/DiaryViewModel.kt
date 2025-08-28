package com.example.danew_app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.usecase.DiaryGetByDateUseCase
import com.example.danew_app.domain.usecase.SaveDiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val saveDiaryUseCase:SaveDiaryUseCase,
    private val diaryGetByDateUseCase: DiaryGetByDateUseCase,
    private val userDataSource: UserDataSource,
) : ViewModel(){

    var content by mutableStateOf<String>("")
    var createdAt by mutableStateOf<String?>(null)

    var saveSuccess by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun saveDiary(){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                val diary = saveDiaryUseCase.invoke(content, token)
                saveSuccess = true
                Log.i("Diary 저장", "${saveSuccess}: ${diary}")
            } catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}