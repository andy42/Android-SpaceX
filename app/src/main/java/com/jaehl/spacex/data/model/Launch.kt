package com.jaehl.spacex.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Launch(
    @PrimaryKey val id: String,
    val name: String?,
    val details: String?,
    @Embedded val links: LaunchLinks?,
    @SerializedName("date_unix") val dateUnix: Long?,
    val upcoming: Boolean,
    val success: Boolean,
    @SerializedName("flight_number") val flightNumber: Int,
    val rocket: String?
)