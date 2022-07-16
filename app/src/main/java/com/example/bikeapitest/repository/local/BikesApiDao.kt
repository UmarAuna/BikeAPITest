package com.example.bikeapitest.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bikeapitest.model.Feature

@Dao
interface BikesApiDao {
    @Query("SELECT * FROM Feature")
    fun findAllBikes(): List<Feature>

    @Query("SELECT * FROM Feature")
    fun fetchLocalDataBike(): LiveData<List<Feature>>

    @Query("DELETE FROM Feature")
    fun deleteAllBike()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBike(items: List<Feature>)
}
