package com.example.unlimint.di.module

import com.example.unlimint.di.components.sharedpreferences.JokeSharedPref
import com.example.unlimint.di.components.sharedpreferences.JokeSharedPrefImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sharedPrefModule = module {
 single<JokeSharedPref> { return@single  JokeSharedPrefImpl(androidApplication()) }
}