package com.example.tradingapp.di.koin

import com.example.tradingapp.viewModels.authentication.AuthenticationViewModel
import com.example.tradingapp.viewModels.home.HomeViewModel
import com.example.tradingapp.viewModels.profile.ProfileViewModel
import com.example.tradingapp.viewModels.rewards.RewardsViewModel
import com.example.tradingapp.viewModels.search.SearchViewModel
import com.example.tradingapp.viewModels.settings.SettingsViewModel
import com.example.tradingapp.viewModels.wallet.WalletViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthenticationViewModel(get(),get()) }
    viewModel { WalletViewModel() }
    viewModel { HomeViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { RewardsViewModel() }
    viewModel { SearchViewModel() }
    viewModel { SettingsViewModel() }
}