package com.example.unlimint.network.service

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api")
    suspend fun getJoke(): Response<String>
}