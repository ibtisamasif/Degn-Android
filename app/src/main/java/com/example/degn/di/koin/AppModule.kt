package com.example.degn.di.koin

import com.example.degn.repo.AuthenticationRepo
import com.example.degn.repo.CMCRepo
import com.example.degn.repo.DashboardRepo
import com.example.degn.repo.MainRepo
import com.example.degn.repo.ProfileRepo
import com.example.degn.repo.TransactionRepo
import com.example.degn.repo.WalletRepo
import org.koin.dsl.module

val appModule = module {
    single { MainRepo(get()) }
    single { AuthenticationRepo(get()) }
    single { ProfileRepo(get(),get()) }
    single { WalletRepo(get(),get()) }
    single { DashboardRepo(get(),get(),get()) }
    single { TransactionRepo(get(),get()) }
    single { CMCRepo(get()) }
}