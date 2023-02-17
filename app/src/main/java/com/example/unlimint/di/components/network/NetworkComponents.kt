package com.example.unlimint.di.components.network

import android.content.Context
import com.example.unlimint.BuildConfig
import com.example.unlimint.network.service.ApiService
import com.example.unlimint.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkComponents {
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    fun provideRetrofit( okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

     fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

     fun provideNetworkHelper(context: Context) = NetworkHelper(context)
}