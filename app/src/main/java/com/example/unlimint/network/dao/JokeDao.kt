package com.example.unlimint.network.dao

import com.example.unlimint.di.components.sharedpreferences.JokeSharedPref
import retrofit2.Response

class JokeDao (private val jokeSharedPref: JokeSharedPref) {

    fun getJoke(response: Response<String>) {
        jokeSharedPref.saveJoke(response.body().toString())
    }

}