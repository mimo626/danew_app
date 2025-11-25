package com.example.danew_app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danew_app.data.dto.UpdateUserRequest
import com.example.danew_app.data.dto.UserResponse
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.domain.model.UserModel
import com.example.danew_app.domain.usecase.CheckUserIdUseCase
import com.example.danew_app.domain.usecase.GetUserUseCase
import com.example.danew_app.domain.usecase.InsertUserUseCase
import com.example.danew_app.domain.usecase.LoginUseCase
import com.example.danew_app.domain.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val checkUserIdUseCase: CheckUserIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val userDataSource: UserDataSource,
    ) : ViewModel() {

    var user by mutableStateOf(UserModel())
        private set

    private val _user = MutableStateFlow(UserModel())
    var getUserData: StateFlow<UserModel> = _user

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var signUpResult by mutableStateOf<String?>(null)
        private set

    var loginResult by mutableStateOf<String?>(null)
        private set

    var logoutResult by mutableStateOf<String?>(null)
        private set

    var isUserIdAvailable by mutableStateOf<Boolean?>(null)
        private set

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

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

    fun updateFcmToken(fcmToken: String) {
        user = user.copy(fcmToken = fcmToken)
    }

    // -------------------- 회원가입 --------------------
    fun completeSignup() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val user = insertUserUseCase.invoke(user)
                if(user.userId.isNotEmpty()){
                    signUpResult = "success"
                }else {
                    signUpResult = "fail"
                    errorMessage = "회원가입 실패"
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                signUpResult = "fail"
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
                val response:UserResponse = loginUseCase.invoke(userId, password)
                if (response.token.isNotEmpty()) {
                    loginResult = "success"
                } else {
                    loginResult = "fail"
                    errorMessage = "토큰 없음 로그인 실패"
                }
            } catch (e: Exception) {
                loginResult = "fail"
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }


    fun logout() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                userDataSource.logout()
                logoutResult = "success"
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                logoutResult = "fail"
            } finally {
                isLoading = false
            }
        }
    }


    // -------------------- 유저 조회 --------------------
    fun getUser(){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                val userData = getUserUseCase.invoke(token)
                _user.value = userData
            }catch (e: Exception) {
                errorMessage = e.localizedMessage
                Log.e("User 조회", "getUser 오류", e)
            } finally {
                isLoading = false
            }
        }
    }

    // -------------------- 유저 수정 --------------------
    fun updateUser(updateUserRequest: UpdateUserRequest){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                val userData = updateUserUseCase.invoke(token, updateUserRequest)
                _user.value = userData
            }catch (e: Exception) {
                errorMessage = e.localizedMessage
                Log.e("User 수정", "updateUser 오류", e)
            } finally {
                isLoading = false
            }
        }
    }

    // -------------------- 키워드 수정 --------------------
    fun updateKeyword(keywordList: List<String>){
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val token = userDataSource.getToken() ?: ""
                val userData = updateUserUseCase.keyword(token, keywordList)
                updateKeyword(keywordList)
                _user.value = userData
            }catch (e: Exception) {
                errorMessage = e.localizedMessage
                Log.e("User 수정", "updateKeyword 오류", e)
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
                isUserIdAvailable = checkUserIdUseCase.invoke(userId)
            } catch (e: Exception) {
                isUserIdAvailable = false
                errorMessage = e.localizedMessage
                Log.e("User 로그인 여부 확인", "checkUserId 오류", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun checkLoginState() {
        viewModelScope.launch {
            _isLoggedIn.value = loginUseCase.isUserLoggedIn()
        }
    }
}

