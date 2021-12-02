package com.jaehl.spacex.data.repository

import kotlinx.coroutines.flow.Flow
import com.jaehl.spacex.data.model.Result
import com.jaehl.spacex.data.model.Launch

interface LaunchesRepository {
    fun getLaunches() : Flow<Result<List<Launch>>>
    fun getLaunch(id : String) : Flow<Result<Launch>>
}