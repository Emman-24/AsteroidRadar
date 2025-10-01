package com.emman.android.asteroidradar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroids: List<AsteroidEntity>)

    @Query("SELECT * FROM asteroids WHERE closeApproachDate >= :startDate ORDER BY closeApproachDate ASC")
    suspend fun getAsteroidsFromDate(startDate: String): List<AsteroidEntity>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    suspend fun getAsteroidsFromDateRange(startDate: String, endDate: String): List<AsteroidEntity>

    @Query("SELECT * FROM asteroids ORDER BY closeApproachDate ASC")
    suspend fun getAllAsteroids(): List<AsteroidEntity>

    @Query("DELETE FROM asteroids WHERE closeApproachDate < :date")
    suspend fun deleteOldAsteroids(date: String)

    @Query("SELECT * FROM asteroids WHERE id = :id")
    suspend fun getAsteroidById(id: Long): AsteroidEntity

}