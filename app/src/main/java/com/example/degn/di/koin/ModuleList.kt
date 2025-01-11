package com.example.degn.di.koin

import com.example.degn.di.pref.prefModule
import org.koin.core.module.Module

fun getListOfModules(): List<Module>{

    return (listOf(
        viewModelModule,
        prefModule,
        networkModule,
        appModule
    ))
}