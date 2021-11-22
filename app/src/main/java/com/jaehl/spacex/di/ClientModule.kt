package com.jaehl.spacex.di

import com.jaehl.spacex.data.remote.spacexApi.SpacexApi
import com.jaehl.spacex.data.remote.spacexApi.SpacexClient
import com.jaehl.spacex.data.remote.spacexApi.SpacexClientImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ClientModule {

    @Provides
    fun spacexClient(api : SpacexApi) : SpacexClient {
        return SpacexClientImp(api)
    }
}