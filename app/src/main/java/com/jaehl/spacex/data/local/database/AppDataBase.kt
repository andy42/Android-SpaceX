package com.jaehl.spacex.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jaehl.spacex.data.model.Launch

@Database(entities = [Launch::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun launchDao() : LaunchDao
}