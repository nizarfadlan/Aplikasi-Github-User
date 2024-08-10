package com.nizarfadlan.aplikasigithubuser.di

import com.nizarfadlan.aplikasigithubuser.ui.screen.detailsScreen.DetailViewModel
import com.nizarfadlan.aplikasigithubuser.ui.screen.homeScreen.HomeViewModel
import com.nizarfadlan.aplikasigithubuser.ui.screen.settingScreen.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModel { HomeViewModel(get()) }
        viewModel { DetailViewModel(get()) }
        viewModel { SettingViewModel(get()) }
    }