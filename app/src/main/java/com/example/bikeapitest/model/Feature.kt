package com.example.bikeapitest.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity
data class Feature(
    @SerializedName("geometry")
    val geometry: Geometry,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("properties")
    val properties: PropertiesBike,
    @SerializedName("type")
    val type: String
) : Parcelable
