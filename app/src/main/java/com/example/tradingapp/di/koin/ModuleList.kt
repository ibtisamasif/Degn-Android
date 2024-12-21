package com.example.tradingapp.di.koin

import com.example.tradingapp.di.pref.prefModule
import org.koin.core.module.Module

fun getListOfModules(): List<Module>{

    return (listOf(
        viewModelModule,
        prefModule,
        networkModule,
        appModule
    ))
}