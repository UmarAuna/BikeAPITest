package com.example.bikeapitest.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bikeapitest.model.*
import com.example.bikeapitest.repository.converters.AbstractConverter

@Database(
    entities = [
        Bike::class,
        Crs::class,
        Feature::class,
        Geometry::class,
        Properties::class,
        PropertiesBike::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    CrsConverter::class,
    FeatureConverter::class,
    GeometryConverter::class,
    PropertiesConverter::class,
    PropertiesBikeConverter::class,
    CoordinatesConverter::class
)

abstract class BikeApiDatabase : RoomDatabase() {
    abstract val bikesApiDao: BikesApiDao
}

private class CrsConverter : AbstractConverter<Crs>(Crs::class, Array<Crs>::class)
private class FeatureConverter : AbstractConverter<Feature>(Feature::class, Array<Feature>::class)
private class GeometryConverter : AbstractConverter<Geometry>(Geometry::class, Array<Geometry>::class)
private class PropertiesConverter : AbstractConverter<Properties>(Properties::class, Array<Properties>::class)
private class PropertiesBikeConverter : AbstractConverter<PropertiesBike>(PropertiesBike::class, Array<PropertiesBike>::class)
