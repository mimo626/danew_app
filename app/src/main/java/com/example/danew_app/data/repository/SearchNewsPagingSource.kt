package com.example.danew_app.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.data.remote.NewsApi

class SearchNewsPagingSource(
    private val api: NewsApi,
    private val searchQuery: String
) : PagingSource<String, NewsEntity>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, NewsEntity> {
        Log.d("SearchNewsPagingSource", "뉴스 페이지")
        return try {
            val page = params.key
            val response = api.fetchNewsBySearchQuery(
                searchQuery = searchQuery,
                page = page)

            Log.d("SearchNewsPagingSource", "뉴스 페이지 ${page}")

            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = response.nextPage
            )
        } catch (e: Exception) {
            Log.e("SearchNewsPagingSource 에러", "메시지: ${e}")

            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, NewsEntity>): String? {
        // 커서 기반일 때는 null 리턴해서 처음부터 다시 로드
        return null
    }
}