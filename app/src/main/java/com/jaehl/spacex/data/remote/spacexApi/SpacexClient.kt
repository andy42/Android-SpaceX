package com.jaehl.spacex.data.remote.spacexApi

import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.data.model.NetworkResult

interface SpacexClient {
    fun getLaunches(): NetworkResult<List<Launch>>
}