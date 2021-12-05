package com.jaehl.spacex.di

import android.content.Context
import com.jaehl.spacex.data.local.configuration.Configuration
import com.jaehl.spacex.data.local.configuration.ConfigurationImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModel {

    @Provides
    fun configuration(@ApplicationContext appContext: Context): Configuration {
        return ConfigurationImp(appContext)
    }
}