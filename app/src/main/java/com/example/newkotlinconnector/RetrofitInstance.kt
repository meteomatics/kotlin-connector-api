package com.example.newkotlinconnector

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    val httpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .callTimeout(300, TimeUnit.SECONDS)
        .build()

    val api: ParametersAPIInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.meteomatics.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(ParametersAPIInterface::class.java)
    }
}