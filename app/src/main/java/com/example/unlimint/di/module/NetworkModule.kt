package com.example.unlimint.di.module

import com.example.unlimint.di.components.network.NetworkComponents
import com.example.unlimint.network.service.ApiHelper
import com.example.unlimint.network.service.ApiHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    single { NetworkComponents.provideRetrofit(get()) }
    single { NetworkComponents.provideOkHttpClient() }
    single { NetworkComponents.provideApiService(get()) }
    single { NetworkComponents.provideNetworkHelper(androidContext()) }
    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }
}
