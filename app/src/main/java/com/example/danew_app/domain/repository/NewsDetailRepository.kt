package com.example.danew_app.domain.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.danew_app.data.entity.NewsDetailType
import com.example.danew_app.data.local.UserDataSource
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.data.repository.NewsRepositoryImpl
import com.example.danew_app.domain.model.NewsModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class NewsDetailRepository @Inject constructor(
    private val newsRepository: NewsRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val todayNewsRepository: TodayNewsRepository,
    private val userDataSource: UserDataSource,
    ) {
    // 뷰모델은 이 함수 하나만 호출하면 됨
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getNewsDetail(id: String, type: NewsDetailType): NewsModel {
        return when (type) {
            // 외부 API 호출 후 NewsDetail로 변환(Mapper)하여 리턴
            NewsDetailType.HOME -> {
                newsRepository.getNewsById(id).first().toDomain()
            }
            NewsDetailType.SEARCH -> {
                newsRepository.getNewsById(id).first().toDomain()
            }
            NewsDetailType.CATEGORY -> {
                newsRepository.getNewsById(id).first().toDomain()
            }
            NewsDetailType.BOOKMARK -> {
                // 내 서버 API 호출 후 변환하여 리턴
                bookmarkRepository.getBookmarkNews(id).toDomain()
            }
            NewsDetailType.TODAY -> {
                // 로컬 DB 조회 후 변환하여 리턴
                val userToken = userDataSource.getToken() ?: ""
                todayNewsRepository.getTodayNews(userId = userToken, newsId = id).toDomain()
            }
        }
    }
}