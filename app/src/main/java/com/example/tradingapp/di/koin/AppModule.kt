package com.example.tradingapp.di.koin

import com.example.tradingapp.repo.AuthenticationRepo
import com.example.tradingapp.repo.DashboardRepo
import com.example.tradingapp.repo.MainRepo
import com.example.tradingapp.repo.ProfileRepo
import com.example.tradingapp.repo.TransactionRepo
import com.example.tradingapp.repo.WalletRepo
import org.koin.dsl.module

val appModule = module {
    single { MainRepo(get()) }
    single { AuthenticationRepo(get()) }
    single { ProfileRepo(get(),get()) }
    single { WalletRepo(get(),get()) }
    single { DashboardRepo(get(),get(),get()) }
    single { TransactionRepo(get(),get()) }
}