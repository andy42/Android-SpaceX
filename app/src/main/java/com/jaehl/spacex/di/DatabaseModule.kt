package com.jaehl.spacex.di

import android.content.Context
import androidx.room.Room
import com.jaehl.spacex.data.local.database.AppDataBase
import com.jaehl.spacex.data.local.database.LaunchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext : Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "AppDatabase"
        ).build()
    }

    @Provides
    fun providesLaunchDao(appDataBase: AppDataBase) : LaunchDao {
        return appDataBase.launchDao()
    }
}