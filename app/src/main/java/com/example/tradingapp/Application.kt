package com.example.tradingapp

import android.app.Application
import com.example.tradingapp.di.koin.getListOfModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(getListOfModules())
        }
    }
}