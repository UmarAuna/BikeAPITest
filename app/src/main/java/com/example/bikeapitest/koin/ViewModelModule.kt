package com.example.bikeapitest.koin

import com.example.bikeapitest.viewmodel.BikeApiViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BikeApiViewModel(get(), get(), get(), androidContext()) }
}
