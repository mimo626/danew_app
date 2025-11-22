package com.example.danew_app.domain.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.danew_app.data.entity.TodayNewsEntity
import com.example.danew_app.data.local.TodayNewsDao
import com.example.danew_app.data.mapper.toTodayNews
import com.example.danew_app.domain.model.NewsModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class TodayNewsRepository @Inject constructor(
    private val dao: TodayNewsDao
) {
    // 1. 오늘 00:00:00의 타임스탬프(Long) 구하기
    private fun getStartOfDay(): Long {
        return LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    // 2. 오늘 23:59:59의 타임스탬프(Long) 구하기
    private fun getEndOfDay(): Long {
        return LocalDate.now()
            .atTime(LocalTime.MAX)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    // 조회: userId와 시간 범위를 넘겨줍니다.
    suspend fun getTodayNewsList(userId: String): List<TodayNewsEntity> {
        return dao.getTodayNewsList(userId, getStartOfDay(), getEndOfDay())
    }

    // 단건 조회: userId도 같이 확인해야 안전합니다.
    suspend fun getTodayNews(userId: String, newsId: String): TodayNewsEntity {
        return dao.getTodayNews(userId, newsId)
    }

    // 저장: NewsModel을 Entity로 변환할 때 userId를 넣어줘야 합니다.
    suspend fun addNews(userId: String, newsModel: NewsModel) {
        val entity = newsModel.toTodayNews().copy(userId = userId)
        dao.insertNews(entity)
    }

    // 오래된 뉴스 삭제: 오늘 0시 이전 데이터는 모두 삭제
    suspend fun clearOldNews() {
        dao.clearOldNews(getStartOfDay())
    }

    // 전체 삭제: 특정 유저의 데이터만 삭제
    suspend fun clearAll(userId: String) {
        dao.clearAll(userId)
    }
}