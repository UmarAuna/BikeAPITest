package com.example.bikeapitest.repository.local

import androidx.room.TypeConverter

class CoordinatesConverter {
    @TypeConverter
    fun fromCoordinates(list: List<Double>?): String {
        return list?.joinToString(separator = ",") { it.toString() } ?: ""
    }

    @TypeConverter
    fun toCoordinates(string: String?): List<Double> {
        return ArrayList(string?.split(",")?.mapNotNull { it.toDoubleOrNull() } ?: emptyList())
    }
}