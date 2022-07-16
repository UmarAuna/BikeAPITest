package com.example.bikeapitest.koin

import com.example.bikeapitest.repository.repo.BikeApiRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { BikeApiRepository(get(), get()) }
}