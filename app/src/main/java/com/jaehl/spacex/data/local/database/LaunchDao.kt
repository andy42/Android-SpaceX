package com.jaehl.spacex.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaehl.spacex.data.model.Launch

@Dao
interface LaunchDao {
    @Query("SELECT * FROM launch")
    fun getAll(): List<Launch>

    @Query("SELECT * FROM launch WHERE id=:id")
    fun get(id: String): Launch?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(launch: Launch)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(launches: List<Launch>)

    @Query("DELETE FROM launch WHERE id=:id")
    fun delete(id: String)
}