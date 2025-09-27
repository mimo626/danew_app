package com.example.danew_app.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.danew_app.data.entity.NewsEntity
import com.example.danew_app.data.remote.NewsApi

class NewsPagingSource(
    private val api: NewsApi,
    private val category: String? = null,
    private val searchQuery: String? = null

) : PagingSource<String, NewsEntity>() { // 키 타입을 String 으로 변경

    override suspend fun load(params: LoadParams<String>): LoadResult<String, NewsEntity> {
        return try {

            val page: String? = params.key // 첫 호출은 null
            val response = when {
                !searchQuery.isNullOrEmpty() -> api.fetchNewsBySearchQuery(
                    searchQuery = searchQuery,
                    page = page)
                !category.isNullOrEmpty() -> api.fetchNewsByCategory(
                    category = category,
                    page = page)
                else -> throw IllegalArgumentException("category or searchQuery must be provided")
            }

            Log.d("NewsPagingSource", "뉴스 페이지 ${page}")


            val newsList = response.results
            Log.d("NewsPagingSource", "뉴스 리스트 ${newsList}")

            LoadResult.Page(
                data = newsList,
                prevKey = null, // 커서 기반 API는 뒤로 가기(prev) 보통 지원 안 함
                nextKey = response.nextPage // 서버에서 내려주는 다음 페이지 토큰
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, NewsEntity>): String? {
        // 커서 기반일 때는 null 리턴해서 처음부터 다시 로드
        return null
    }
}
