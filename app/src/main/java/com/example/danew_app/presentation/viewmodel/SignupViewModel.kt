package com.example.danew_app.presentation.viewmodel

import UserModel
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.dto.LoginResponse
import com.example.danew_app.domain.usecase.CheckUserIdUseCase
import com.example.danew_app.domain.usecase.InsertUserUseCase
import com.example.danew_app.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val checkUserIdUseCase: CheckUserIdUseCase
) : ViewModel() {

    var user by mutableStateOf(UserModel())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var loginResult by mutableStateOf<String?>(null)
        private set

    var isUserIdAvailable by mutableStateOf<Boolean?>(null)
        private set


    // -------------------- 유저 정보 업데이트 --------------------
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

    fun updateKeywordList(keywordList: List<String>) {
        user = user.copy(keywordList = keywordList)
    }

    fun updateCreatedAt(createdAt: String) {
        user = user.copy(createdAt = createdAt)
    }


    // -------------------- 회원가입 --------------------
    fun completeSignup() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                insertUserUseCase(user)
                Log.d("User", "SignupViewModel_completeSignup: $user")
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // -------------------- 로그인 --------------------
    fun login(userId: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response:LoginResponse = loginUseCase(userId, password)
                if (response.success) {
                    loginResult = "success"
                } else {
                    loginResult = "fail"
                    errorMessage = response.message
                }
            } catch (e: Exception) {
                loginResult = "fail"
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    // -------------------- 아이디 중복체크 --------------------
    fun checkUserId(userId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                checkUserIdUseCase(userId){ available ->
                    isUserIdAvailable = available
                }
            } catch (e: Exception) {
                isUserIdAvailable = false
                errorMessage = e.localizedMessage
                Log.e("User", "SignupViewModel_checkUserId 중복", e)
            } finally {
                isLoading = false
            }
        }
    }
}

