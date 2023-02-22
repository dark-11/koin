package com.example.unlimint.di.module

import com.example.unlimint.network.dao.JokeDao
import com.example.unlimint.network.repository.JokeRepository
import org.koin.dsl.module

val repoModule = module {
    single { JokeRepository(get(),get(),get()) }
    single { JokeDao(get()) }
}