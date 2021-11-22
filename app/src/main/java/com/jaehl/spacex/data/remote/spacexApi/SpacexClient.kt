package com.jaehl.spacex.data.remote.spacexApi

import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.data.model.Result

interface SpacexClient {
    fun getLaunches() : Result<List<Launch>>
}