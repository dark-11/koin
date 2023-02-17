package com.example.unlimint.di.components.sharedpreferences

interface JokeSharedPref {
    fun saveJoke(joke:String)
    fun getJoke():List<String>
    fun clear()
}