package com.emman.android.asteroidradar.domain.repository

import com.emman.android.asteroidradar.domain.models.Asteroid
import com.emman.android.asteroidradar.domain.models.PictureDay

interface AsteroidRepository {
    suspend fun getAsteroids(): List<Asteroid>
    suspend fun getImageOfTheDay(): PictureDay
    suspend fun getAsteroid(id: Long): Asteroid

}