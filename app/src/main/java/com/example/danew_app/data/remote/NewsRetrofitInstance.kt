package com.example.danew_app.data.remote//
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//class RetrofitClient {
//    var retrofit: Retrofit? = null
//        private set
//
//    init {
//        initializeRetrofit()
//    }
//
//    private fun initializeRetrofit() {
//        retrofit = Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:8080")
//            .addConverterFactory(GsonConverterFactory.create(Gson()))
//            .build()
//    }
//
//}
// Retrofit 객체를 생성하고, 싱글톤으로 유지
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

@Module
@InstallIn(SingletonComponent::class)
object UserRetrofitInstance {
    @get:Provides
    @Singleton
    val userApi: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}
