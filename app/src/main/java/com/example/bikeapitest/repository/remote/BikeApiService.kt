package com.example.bikeapitest.repository.remote

import com.example.bikeapitest.model.Bike
import retrofit2.Response
import retrofit2.http.GET

interface BikeApiService {

    @GET("/mim/plan/map_service.html?mtype=pub_transport&co=stacje_rowerowe")
    suspend fun getBikeApi(): Response<Bike>
}