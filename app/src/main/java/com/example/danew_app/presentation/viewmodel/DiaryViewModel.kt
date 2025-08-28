package com.example.danew_app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.model.DiaryModel
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
    var getCreatedAt by mutableStateOf<String>("")
    var saveCreatedAt by mutableStateOf<String>("")

    var saveSuccess by mutableStateOf(false)
        private set

    var diary by mutableStateOf<DiaryModel?>(null)


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
                val diary = saveDiaryUseCase.invoke(content, saveCreatedAt, token)
                saveSuccess = true
                Log.i("Diary 저장", "${saveSuccess}: ${diary}")
            } catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun getDiaryByDate(){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            diary = null
            try {
                val token = userDataSource.getToken() ?: ""
                diary = diaryGetByDateUseCase.invoke(token, getCreatedAt)
                Log.i("Diary 날짜별 조회", "${diary}")
            } catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading =false
            }
        }
    }
}