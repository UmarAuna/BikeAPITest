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
data class PropertiesBike(
    @PrimaryKey
    @SerializedName("room_id")
    val roomId: Int?,
    @SerializedName("bike_racks")
    val bikeRacks: String,
    @SerializedName("bikes")
    val bikes: String,
    @SerializedName("free_racks")
    val freeRacks: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("updated")
    val updated: String
) : Parcelable
