package com.example.tradingapp.di.koin

import com.example.tradingapp.viewModels.authentication.AuthenticationViewModel
import com.example.tradingapp.viewModels.export.ExportViewModel
import com.example.tradingapp.viewModels.home.HomeViewModel
import com.example.tradingapp.viewModels.profile.ProfileViewModel
import com.example.tradingapp.viewModels.rewards.RewardsViewModel
import com.example.tradingapp.viewModels.search.SearchViewModel
import com.example.tradingapp.viewModels.settings.SettingsViewModel
import com.example.tradingapp.viewModels.wallet.WalletViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin

val viewModelModule = module {
    single { AuthenticationViewModel(get(), get()) }
    viewModel { WalletViewModel(get(),get()) }
    single { HomeViewModel(get(), get(),get(),get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { RewardsViewModel() }
    viewModel { SearchViewModel() }
    viewModel { SettingsViewModel(get()) }
    single { ExportViewModel(get(), get()) }
}