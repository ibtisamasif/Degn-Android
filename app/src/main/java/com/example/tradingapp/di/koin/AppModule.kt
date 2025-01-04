package com.example.tradingapp.di.koin

import com.example.tradingapp.repo.AuthenticationRepo
import com.example.tradingapp.repo.DashboardRepo
import com.example.tradingapp.repo.ProfileRepo
import com.example.tradingapp.repo.TransactionRepo
import com.example.tradingapp.repo.WalletRepo
import org.koin.dsl.module

val appModule = module {
    single { AuthenticationRepo(get()) }
    single { ProfileRepo(get()) }
    single { WalletRepo(get()) }
    single { DashboardRepo(get(),get()) }
    single { TransactionRepo(get()) }
}