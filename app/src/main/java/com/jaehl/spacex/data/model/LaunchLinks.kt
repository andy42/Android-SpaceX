package com.jaehl.spacex.data.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class LaunchLinks(
    @Embedded val patch : LaunchPatch?,
    val presskit : String?,
    val webcast : String?,
    @SerializedName("youtube_id") val youtubeId : String?,
    val article : String?,
    val wikipedia : String?
)