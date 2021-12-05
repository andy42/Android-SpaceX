package com.jaehl.spacex.data.repository

import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.data.model.Result
import kotlinx.coroutines.flow.Flow

interface LaunchesRepository {
    fun getLaunches(): Flow<Result<List<Launch>>>
    fun getLaunch(id: String): Flow<Result<Launch>>
}