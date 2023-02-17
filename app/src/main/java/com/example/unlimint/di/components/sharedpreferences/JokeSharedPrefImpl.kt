package com.example.unlimint.di.components.sharedpreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class JokeSharedPrefImpl (private val context: Context): SharedPrefManager,JokeSharedPref {

    companion object {
        private const val JOKE_PREFERENCE_NAME = "JokePreferences"
    }
    override val sharedPreferences: SharedPreferences
        get() =    SecureSharedPrefProvider.getSharedPreference(context, JOKE_PREFERENCE_NAME)

    override fun saveJoke(joke: String) {
        saveBoolean(joke,true)
    }

    override fun getJoke(): List<String> {
        return getFullData()?.filterValues { it == true }?.keys?.toList()!!
    }

    override fun clear() {
        clearPreference()
    }
}