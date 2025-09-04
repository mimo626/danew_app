package com.example.danew_app.data.repository

import android.util.Log
import com.example.danew_app.data.dto.ApiResponse
import com.example.danew_app.data.entity.BookmarkEntity
import com.example.danew_app.data.entity.MetaNewsEntity
import com.example.danew_app.data.remote.BookmarkApi
import com.example.danew_app.domain.repository.BookmarkRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resumeWithException

@Singleton
class BookmarkRepositoryImpl @Inject constructor(
    private val api:BookmarkApi,
) : BookmarkRepository{

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun saveBookmark(token:String, metaNewsEntity: MetaNewsEntity): BookmarkEntity =
        suspendCancellableCoroutine { cont ->
            api.saveBookmark(token, metaNewsEntity).enqueue(object : Callback<ApiResponse<BookmarkEntity>>{
                override fun onResponse(
                    call: Call<ApiResponse<BookmarkEntity>>,
                    response: Response<ApiResponse<BookmarkEntity>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null){
                        if (body.status == "success" && body.data != null) {
                            val bookmark = body.data
                            cont.resume(bookmark){}
                            Log.i("Bookmark 저장", "${bookmark}")

                        }
                        else {
                            val msg = body.message ?: "북마크 저장 실패"
                            Log.e("Bookmark 저장", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "다이어리 저장 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Bookmark 저장", "실패: $errorMsg")                    }
                }

                override fun onFailure(call: Call<ApiResponse<BookmarkEntity>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Bookmark 저장", "네트워크 실패", t)
                }
            })
        }
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getBookmarks(token: String): List<MetaNewsEntity> =
        suspendCancellableCoroutine { cont ->
            api.getBookmarks(token).enqueue(object : Callback<ApiResponse<List<MetaNewsEntity>>>{
                override fun onResponse(
                    call: Call<ApiResponse<List<MetaNewsEntity>>>,
                    response: Response<ApiResponse<List<MetaNewsEntity>>>
                ) {
                    val body = response.body()
                    if (response.isSuccessful && body != null){
                        if (body.status == "success" && body.data != null) {
                            val newsList = body.data
                            cont.resume(newsList){}
                            Log.i("Bookmark 뉴스 조회", "성공: ${newsList.size}")

                        }
                        else {
                            val msg = body.message ?: "북마크 뉴스 조회 실패"
                            Log.e("Bookmark 뉴스 조회", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "북마크 뉴스 조회 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Bookmark 뉴스 조회", "실패: $errorMsg")                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<MetaNewsEntity>>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Bookmark 뉴스 조회", "네트워크 실패", t)
                }
            })
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun checkBookmark(token: String, newsId: String): Boolean =
        suspendCancellableCoroutine { cont ->
            api.checkBookmark(token, newsId).enqueue(object : Callback<ApiResponse<Boolean>> {
                override fun onResponse(call: Call<ApiResponse<Boolean>>, response: Response<ApiResponse<Boolean>>) {
                    val body = response.body()
                    if (response.isSuccessful && body != null){
                        if (body.status == "success" && body.data != null) {
                            val isBookmark = body.data
                            cont.resume(isBookmark){}
                            Log.i("Bookmark 여부 조회", "성공: ${isBookmark}")
                        }
                        else {
                            val msg = body.message ?: "북마크 여부 조회 실패"
                            Log.e("Bookmark 여부 조회 ", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "북마크 삭제 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Bookmark 여부 조회", "실패: $errorMsg")                    }
                }

                override fun onFailure(call: Call<ApiResponse<Boolean>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Bookmark 여부 조회", "네트워크 실패", t)
                }
            })
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun deleteBookmark(token: String, newsId: String): Boolean =
        suspendCancellableCoroutine { cont ->
            api.deleteBookmark(token, newsId).enqueue(object : Callback<ApiResponse<Boolean>> {
                override fun onResponse(call: Call<ApiResponse<Boolean>>, response: Response<ApiResponse<Boolean>>) {
                    val body = response.body()
                    if (response.isSuccessful && body != null){
                        if (body.status == "success" && body.data != null) {
                            val isDelete = body.data
                            cont.resume(isDelete){}
                            Log.i("Bookmark 삭제", "성공: ${isDelete}")

                        }
                        else {
                            val msg = body.message ?: "북마크 삭제 실패"
                            Log.e("Bookmark 삭제", "실패: $msg")
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "북마크 삭제 실패"
                        cont.resumeWithException(RuntimeException(errorMsg))
                        Log.e("Bookmark 삭제", "실패: $errorMsg")                    }
                }

                override fun onFailure(call: Call<ApiResponse<Boolean>>, t: Throwable) {
                    cont.resumeWithException(t)
                    Log.e("Bookmark 삭제", "네트워크 실패", t)
                }
            })
        }

}