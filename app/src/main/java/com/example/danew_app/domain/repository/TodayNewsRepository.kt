package com.example.danew_app.domain.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.danew_app.data.entity.TodayNewsEntity
import com.example.danew_app.data.local.TodayNewsDao
import com.example.danew_app.data.mapper.toTodayNews
import com.example.danew_app.domain.model.NewsModel
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class TodayNewsRepository @Inject constructor(
    private val dao: TodayNewsDao
) {
    private fun today(): String = LocalDate.now().toString()

    suspend fun getTodayNews(): List<TodayNewsEntity> {
        return dao.getTodayNews(today())
    }

    suspend fun addNews(newsModel: NewsModel) {
        dao.insertNews(
            newsModel.toTodayNews()
        )
    }

    suspend fun clearOldNews() {
        dao.clearOldNews(today())
    }

    suspend fun clearAll() {
        dao.clearAll()
    }
}
