package com.example.unlimint.di.components.sharedpreferences

import android.content.SharedPreferences
import androidx.core.content.edit

internal interface SharedPrefManager {

    val sharedPreferences: SharedPreferences

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    fun getFullData(): MutableMap<String, *>? {
        return sharedPreferences.all
    }

    fun clearPreference() {
        sharedPreferences.edit(commit = true) { clear() }
    }

}