package com.jaehl.spacex.data.remote.spacexApi

import com.jaehl.spacex.data.model.Launch
import retrofit2.Call
import retrofit2.http.GET

interface SpacexApi {

    @GET("v4/launches")
    fun getLaunches() : Call<List<Launch>>
}