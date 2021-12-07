package ro.ubb.composetrainingapp.core

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val URL = "http://192.168.1.4:8080/"

    val tokenInterceptor : TokenInterceptor = TokenInterceptor()

    private val  client : OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(tokenInterceptor)
    }.build()

    private var gson = GsonBuilder().setLenient().create()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
}