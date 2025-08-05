package com.example.danew_app.data.remote//
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
object RetrofitInstance {
    val newsApi: NewsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsdata.io/api/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}
