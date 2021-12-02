package com.jaehl.spacex.data.repository

import com.jaehl.spacex.R
import com.jaehl.spacex.data.local.database.LaunchDao
import com.jaehl.spacex.data.remote.spacexApi.SpacexClient
import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.ui.JobDispatcher
import kotlinx.coroutines.flow.*
import com.jaehl.spacex.data.model.Result
import timber.log.Timber

class LaunchesRepositoryImp (private val spacexClient : SpacexClient, private val launchDao: LaunchDao, private val dispatcher: JobDispatcher) : LaunchesRepository {

    override fun getLaunches(): Flow<Result<List<Launch>>> {
        return flow {
            emit(getLaunchesCached())
            emit(getLaunchesRemote())
        }.flowOn(dispatcher.io())
    }

    override fun getLaunch(id: String): Flow<Result<Launch>> {
        return flow {
            emit(Result.success(launchDao.get(id)))
        }.flowOn(dispatcher.io())
    }

    private fun getLaunchesCached() : Result<List<Launch>>{
        return Result.loading(launchDao.getAll())
    }

    private fun saveLaunches(launches : List<Launch>){
        launchDao.insertAll(launches)
    }

    private fun getLaunchesRemote() : Result<List<Launch>>{
        val response = spacexClient.getLaunches()
        return when(response.status){
            Result.Status.SUCCESS -> {
                val newLaunches = response.data ?: arrayListOf()
                saveLaunches(newLaunches)
                Result.success(newLaunches)
            }
            Result.Status.ERROR -> {
                Timber.e(response.error, "LaunchesRepositoryImp")
                Result.error(R.string.generic_error)
            }
            Result.Status.LOADING -> {
                Result.loading()
            }
        }
    }
}