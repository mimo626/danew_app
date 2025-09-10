package com.example.danew_app.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.danew_app.data.local.PreferencesKeys.ACCESS_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object PreferencesKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
}

class UserDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun saveLoginInfo(token: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.IS_LOGGED_IN] = true
            prefs[PreferencesKeys.ACCESS_TOKEN] = token
        }
        Log.i("User 토큰 저장", "${token}")
    }
    suspend fun getToken(): String? {
        return context.dataStore.data.first()[ACCESS_TOKEN]
    }

    suspend fun logout() {

        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.IS_LOGGED_IN] = false
            prefs.remove(PreferencesKeys.ACCESS_TOKEN)
        }
        Log.i("User 로그아웃", "성공")
    }

    suspend fun checkLoginState(): Boolean {
        val prefs = context.dataStore.data.first()
        val isLoggedIn = prefs[PreferencesKeys.IS_LOGGED_IN] ?: false
        val token = prefs[PreferencesKeys.ACCESS_TOKEN]
        val isLogin = isLoggedIn && !token.isNullOrEmpty()
        Log.i("User 로그인 여부 확인", "${isLogin}")
        return isLogin
    }
}
