package com.example.degn.di.koin

import com.example.degn.viewModels.authentication.AuthenticationViewModel
import com.example.degn.viewModels.export.ExportViewModel
import com.example.degn.viewModels.home.HomeViewModel
import com.example.degn.viewModels.profile.ProfileViewModel
import com.example.degn.viewModels.rewards.RewardsViewModel
import com.example.degn.viewModels.search.SearchViewModel
import com.example.degn.viewModels.settings.SettingsViewModel
import com.example.degn.viewModels.wallet.WalletViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { AuthenticationViewModel(get(), get()) }
    viewModel { WalletViewModel(get(),get()) }
    single { HomeViewModel(get(), get(),get(),get(),get(),get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { RewardsViewModel() }
    viewModel { SearchViewModel() }
    viewModel { SettingsViewModel(get()) }
    single { ExportViewModel(get(), get()) }
}