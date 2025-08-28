package com.example.danew_app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.dto.DiaryRequest
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.model.DiaryModel
import com.example.danew_app.domain.usecase.DiaryGetByDateUseCase
import com.example.danew_app.domain.usecase.SaveDiaryUseCase
import com.example.danew_app.domain.usecase.UpdateDiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val saveDiaryUseCase:SaveDiaryUseCase,
    private val diaryGetByDateUseCase: DiaryGetByDateUseCase,
    private val updateDiaryUseCase: UpdateDiaryUseCase,
    private val userDataSource: UserDataSource,
) : ViewModel(){

    var content by mutableStateOf<String>("")
    var getCreatedAt by mutableStateOf<String>("")
    var saveCreatedAt by mutableStateOf<String>("")

    var success by mutableStateOf(false)
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
                diary = saveDiaryUseCase.invoke(content, saveCreatedAt, token)
                success = true
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
            } catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading =false
            }
        }
    }

    fun updateDiary(){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                val diaryRequest = DiaryRequest(content, saveCreatedAt)
                diary = diary?.let { updateDiaryUseCase.invoke(it.diaryId, diaryRequest, token)}
                success = true
            }catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading =false
            }
        }
    }
}