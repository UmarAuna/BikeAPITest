package com.example.bikeapitest.koin

import android.app.Application
import androidx.room.Room
import com.example.bikeapitest.repository.local.BikeApiDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): BikeApiDatabase {
        return Room.databaseBuilder(application, BikeApiDatabase::class.java, "BikeApiDb")
            .fallbackToDestructiveMigration()
            .build()
    }
    fun provideBikesApi(database: BikeApiDatabase) = database.bikesApiDao

    single { provideDatabase(androidApplication()) }
    single { provideBikesApi(get()) }
}