package com.example.mvvmkotlinexample.retrofit

import com.aakriti.assignment.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    val retrofitClient: Retrofit.Builder by lazy {

        val okhttpClient = OkHttpClient.Builder()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }
}
