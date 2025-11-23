package com.example.danew_app.data.remote//
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

//NewsData.io api
@Module
@InstallIn(SingletonComponent::class)
object NewsRetrofitInstance {
    @get:Provides
    @Singleton
    val newsApi: NewsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsdata.io/api/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}

//로컬 서버 api
@Module
@InstallIn(SingletonComponent::class)
object LocalRetrofitModule {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS) // 연결 타임아웃
        .readTimeout(30, TimeUnit.SECONDS)    // 응답 대기 타임아웃
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDiaryApi(retrofit: Retrofit): DiaryApi {
        return retrofit.create(DiaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBookmarkApi(retrofit: Retrofit) : BookmarkApi{
        return retrofit.create(BookmarkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAIApi(retrofit: Retrofit) : AIApi{
        return retrofit.create(AIApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuestionApi(retrofit: Retrofit) : QuestionApi{
        return retrofit.create(QuestionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAnnounceApi(retrofit: Retrofit) : AnnounceApi{
        return retrofit.create(AnnounceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationApi(retrofit: Retrofit) : NotificationApi{
        return retrofit.create(NotificationApi::class.java)
    }
}

