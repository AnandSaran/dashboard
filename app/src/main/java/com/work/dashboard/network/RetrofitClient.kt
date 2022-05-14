package com.work.dashboard.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.work.dashboard.BuildConfig.DEBUG
import com.work.dashboard.network.jsonadapter.NullToEmptyStringAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val TIME_OUT_WRITE: Long = 120
    private const val TIME_OUT_READ: Long = 120
    private const val TIME_OUT_CONNECT: Long = 120

    private val loggingLevel = if (DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }

    private var httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(loggingLevel)
    private var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)  // connect timeout
        .writeTimeout(TIME_OUT_WRITE, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT_READ, TimeUnit.SECONDS)      // socket timeout
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val moShi = Moshi.Builder()
        .add(NullToEmptyStringAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    private val mRetrofit = Retrofit.Builder()
        .baseUrl("https://green-thumb-64168.uc.r.appspot.com/")
        .addConverterFactory(MoshiConverterFactory.create(moShi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()

    val retrofit: Retrofit
        get() = mRetrofit
}