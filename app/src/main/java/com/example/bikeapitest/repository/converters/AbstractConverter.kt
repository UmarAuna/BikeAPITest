package com.example.bikeapitest.repository.converters

import androidx.room.TypeConverter
import com.example.bikeapitest.extensions.toJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass

abstract class AbstractConverter<T : Any>(
    private val clazz: KClass<T>,
    private val listClazz: KClass<Array<T>>
) {

    @TypeConverter
    fun toItem(json: String): T? {
        return Gson().fromJson(json, clazz.java)
    }

    @TypeConverter
    fun toItems(json: String): List<T>? {
        val x: KClass<Array<Any?>> = Array<Any?>::class
        return Gson().fromJson(json, listClazz.java).asList()
        return Gson().fromJson(json, object : TypeToken<List<T>>() {}.type)
    }

    @TypeConverter
    fun fromItem(item: T?): String {
        return item.toJson()
    }

    @TypeConverter
    fun fromItems(list: List<T>?): String {
        return list.toJson()
    }
}
