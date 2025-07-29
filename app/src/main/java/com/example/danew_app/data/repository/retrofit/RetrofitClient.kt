//
//import com.google.gson.Gson
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
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
//}