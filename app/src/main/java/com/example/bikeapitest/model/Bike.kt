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
data class Bike(
    @PrimaryKey
    @SerializedName("room_id")
    val roomId: Int?,
    @SerializedName("crs")
    val crs: Crs,
    @SerializedName("features")
    val features: List<Feature>,
    @SerializedName("type")
    val type: String
) : Parcelable
