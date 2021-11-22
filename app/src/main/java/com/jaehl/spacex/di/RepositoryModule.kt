package com.jaehl.spacex.di

import com.jaehl.spacex.data.local.database.LaunchDao
import com.jaehl.spacex.data.repository.LaunchesRepository
import com.jaehl.spacex.data.repository.LaunchesRepositoryImp
import com.jaehl.spacex.data.remote.spacexApi.SpacexClient
import com.jaehl.spacex.ui.JobDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun LaunchesRepository (spacexClient : SpacexClient, launchDao: LaunchDao, dispatcher: JobDispatcher) : LaunchesRepository {
        return LaunchesRepositoryImp(spacexClient, launchDao, dispatcher)
    }
}