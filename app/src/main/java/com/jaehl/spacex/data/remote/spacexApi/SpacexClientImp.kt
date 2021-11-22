package com.jaehl.spacex.data.remote.spacexApi

import com.jaehl.spacex.R
import com.jaehl.spacex.data.model.Launch
import com.jaehl.spacex.data.model.Result
import timber.log.Timber

class SpacexClientImp(private val spacexApi : SpacexApi) : SpacexClient {
    override fun getLaunches(): Result<List<Launch>> {
        try {
            val response = spacexApi.getLaunches().execute()
            if(response.isSuccessful){
                return Result.success(response.body()!!)
            }
            Timber.e("SpacexClientImp code:${response.code()}, error:${response.errorBody()?.toString()}")
            return Result.error(R.string.generic_error)
        }
        catch (t : Throwable){
            Timber.e(t,"SpacexClientImp")
            return Result.error(R.string.generic_error, t)
        }
    }
}