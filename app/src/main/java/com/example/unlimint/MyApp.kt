package com.example.unlimint

import android.app.Application
import com.example.unlimint.di.module.networkModule
import com.example.unlimint.di.module.repoModule
import com.example.unlimint.di.module.sharedPrefModule
import com.example.unlimint.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MyApp)
            modules(networkModule, repoModule, viewModelModule,sharedPrefModule)
        }
    }
}