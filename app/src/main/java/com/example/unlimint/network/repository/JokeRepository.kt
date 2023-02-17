package com.example.unlimint.network.repository

import com.example.unlimint.network.service.ApiHelper

class JokeRepository constructor(private val apiHelper: ApiHelper) {

    suspend fun getJoke() = apiHelper.getJokes()
}