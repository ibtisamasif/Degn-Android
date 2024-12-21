package com.example.tradingapp.di.koin

import com.example.tradingapp.repo.AuthenticationRepo
import org.koin.dsl.module

val appModule = module {
    single { AuthenticationRepo(get()) }
}