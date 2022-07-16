package com.example.bikeapitest.repository.repo

import android.util.Log
import com.example.bikeapitest.model.Bike
import com.example.bikeapitest.repository.local.BikesApiDao
import com.example.bikeapitest.repository.remote.BikeApiService
import com.example.bikeapitest.extensions.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class BikeApiRepository(
    private val api: BikeApiService,
    private val dao: BikesApiDao,
) {

    suspend fun getBikeApi(): Response<Bike> {
        val response = api.getBikeApi()
        Log.d(TAG, "${response.body()}")

        response.body()?.let {
            withContext(Dispatchers.IO) {
                dao.deleteAllBike()
                dao.addBike(it.features)
            }
        }
        return response
    }
}
