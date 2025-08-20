package com.example.danew_app.presentation.viewmodel

import UserModel
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.domain.usecase.InsertUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase,
) : ViewModel() {
    var user by mutableStateOf(UserModel())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun updateUserId(id: String) {
        user = user.copy(userId = id)
    }

    fun updatePassword(pw: String) {
        user = user.copy(password = pw)
    }

    fun updateName(name: String) {
        user = user.copy(name = name)
    }

    fun updateAge(age: Int) {
        user = user.copy(age = age)
    }

    fun updateGender(gender: String) {
        user = user.copy(gender = gender)
    }

    fun updateKeywordList(keywordList: List<String>){
        user = user.copy(keywordList = keywordList)
    }

    fun updateCreatedAtList(createdAt: String){
        user = user.copy(createdAt = createdAt)
    }

    fun completeSignup() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                insertUserUseCase.invoke(user)
                Log.d("User", "SignupViewModel_completeSignup: $user")
            } catch (e:Exception){
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}
