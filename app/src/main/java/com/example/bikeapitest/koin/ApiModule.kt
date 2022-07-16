package com.example.bikeapitest.koin

import com.example.bikeapitest.repository.remote.BikeApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideBikeApi(retrofit: Retrofit): BikeApiService {
        return retrofit.create(BikeApiService::class.java)
    }

    single { provideBikeApi(get()) }
}
