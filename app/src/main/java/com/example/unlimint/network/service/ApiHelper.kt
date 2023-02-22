package com.example.unlimint.network.service

import retrofit2.Response

interface ApiHelper {
  suspend fun getJokes(): Response<String>
}