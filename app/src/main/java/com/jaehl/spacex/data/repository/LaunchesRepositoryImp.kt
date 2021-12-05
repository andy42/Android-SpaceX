package com.jaehl.spacex.data.repository

import com.jaehl.spacex.data.local.database.LaunchDao
import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.data.model.NetworkResult
import com.jaehl.spacex.data.model.Result
import com.jaehl.spacex.data.remote.spacexApi.SpacexClient
import com.jaehl.spacex.ui.JobDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class LaunchesRepositoryImp(
    private val spacexClient: SpacexClient,
    private val launchDao: LaunchDao,
    private val dispatcher: JobDispatcher
) : LaunchesRepository {

    override fun getLaunches(): Flow<Result<List<Launch>>> {
        return flow {
            emit(getLaunchesCached())
            emit(getLaunchesRemote())
        }.flowOn(dispatcher.io())
    }

    override fun getLaunch(id: String): Flow<Result<Launch>> {
        return flow {
            val launch = launchDao.get(id)
            if (launch == null) {
                emit(Result.error(Exception()))
            }
            launch?.let { emit(Result.success(it)) }

        }.flowOn(dispatcher.io())
    }

    private fun getLaunchesCached(): Result<List<Launch>> {
        return Result.loading(launchDao.getAll())
    }

    private fun saveLaunches(launches: List<Launch>) {
        launchDao.insertAll(launches)
    }

    private fun getLaunchesRemote(): Result<List<Launch>> {
        return when (val response = spacexClient.getLaunches()) {
            is NetworkResult.Success -> {
                val newLaunches = response.data
                saveLaunches(newLaunches)
                Result.success(newLaunches)
            }
            is NetworkResult.Error -> {
                Timber.e(response.error, "LaunchesRepositoryImp")
                Result.error(response.error ?: Exception())
            }
        }
    }
}