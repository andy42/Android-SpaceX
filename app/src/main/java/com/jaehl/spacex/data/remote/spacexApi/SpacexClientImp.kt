package com.jaehl.spacex.data.remote.spacexApi

import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.data.model.NetworkResult
import timber.log.Timber

class SpacexClientImp(private val spacexApi : SpacexApi) : SpacexClient {
    override fun getLaunches(): NetworkResult<List<Launch>> {
        try {
            val response = spacexApi.getLaunches().execute()
            if(response.isSuccessful){
                return NetworkResult.success(response.body()!!)
            }
            Timber.e("SpacexClientImp code:${response.code()}, error:${response.errorBody()?.toString()}")
            return NetworkResult.internalServerError(null)
        }
        catch (t : Throwable){
            Timber.e(t,"SpacexClientImp")
            return NetworkResult.error(t)
        }
    }
}