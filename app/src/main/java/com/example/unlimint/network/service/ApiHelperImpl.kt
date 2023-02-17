package com.example.unlimint.network.service

import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getJokes(): Response<String> = apiService.getJoke()

}