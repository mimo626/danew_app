package com.example.danew_app.data.repository

import android.util.Log
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

@Singleton
class BookmarkRepositoryImpl @Inject constructor(
    private val api:BookmarkApi,
) : BookmarkRepository{

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun saveBookmark(token:String, metaNewsEntity: MetaNewsEntity): BookmarkEntity =
        suspendCancellableCoroutine { cont ->
            api.saveBookmark(token, metaNewsEntity).enqueue(object : Callback<BookmarkEntity>{
                override fun onResponse(
                    call: Call<BookmarkEntity>,
                    response: Response<BookmarkEntity>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            cont.resume(body) {}
                            Log.i("Bookmark 저장", "${response.body()}")

                        } else {
                            Log.e("Bookmark 저장", "응답 바디 없음: ${response.code()}")
                        }
                    } else {
                        Log.e("Bookmark 저장", "서버 오류: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<BookmarkEntity>, t: Throwable) {
                    Log.e("Bookmark 저장", "네트워크 실패", t)
                }
            })
        }
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getBookmarks(token: String): List<MetaNewsEntity> =
        suspendCancellableCoroutine { cont ->
            api.getBookmarks(token).enqueue(object : Callback<List<MetaNewsEntity>>{
                override fun onResponse(
                    call: Call<List<MetaNewsEntity>>,
                    response: Response<List<MetaNewsEntity>>
                ) {
                    if (response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            cont.resume(body){}
                            Log.i("Bookmark 가져오기", "${response.body()}")

                        }
                        else {
                            Log.e("Bookmark 가져오기", "응답 바디 없음: ${response.code()}")
                        }
                    } else {
                        Log.e("Bookmark 가져오기", "서버 오류: ${response.code()}")
                    }                }

                override fun onFailure(call: Call<List<MetaNewsEntity>>, t: Throwable) {
                    Log.e("Bookmark 가져오기", "네트워크 실패", t)
                }
            })
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun deleteBookmark(token: String, newsId: String): Boolean =
        suspendCancellableCoroutine { cont ->
            api.deleteBookmark(token, newsId).enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.body() == true) {
                        cont.resume(true) {}
                        Log.i("Bookmark 삭제", "성공: $newsId")
                    } else {
                        cont.resume(false) {}
                        Log.e("Bookmark 삭제", "서버 오류: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    cont.resume(false) {}
                    Log.e("Bookmark 삭제", "네트워크 실패", t)
                }
            })
        }

}