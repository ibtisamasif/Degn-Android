package com.example.degn.di.pref

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val prefModule = module {
    single { DegnSharedPref.getInstance(androidApplication()) }
}