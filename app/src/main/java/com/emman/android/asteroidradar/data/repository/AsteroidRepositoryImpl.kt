package com.emman.android.asteroidradar.data.repository

import com.emman.android.asteroidradar.data.local.AsteroidDatabase
import com.emman.android.asteroidradar.data.local.toDomainModel
import com.emman.android.asteroidradar.data.local.toEntity
import com.emman.android.asteroidradar.data.remote.NasaAsteroidApi
import com.emman.android.asteroidradar.domain.models.Asteroid
import com.emman.android.asteroidradar.domain.models.PictureDay
import com.emman.android.asteroidradar.domain.repository.AsteroidRepository
import com.emman.android.asteroidradar.utils.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AsteroidRepositoryImpl(private val database: AsteroidDatabase) : AsteroidRepository {

    override suspend fun getAsteroids(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            try {
                val asteroids: List<Asteroid> = fetchAsteroidsFromNetwork()
                if (asteroids.isNotEmpty()) {
                    saveAsteroidToDatabase(asteroids)
                }
                val dbAsteroids = database.asteroidDao.getAllAsteroids()
                return@withContext dbAsteroids.map { it.toDomainModel() }
            } catch (_: Exception) {
                val dbAsteroids = database.asteroidDao.getAllAsteroids()
                return@withContext dbAsteroids.map { it.toDomainModel() }
            }
        }
    }


    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val response: String = NasaAsteroidApi.retrofitService.getAsteroids().await()
            val jsonObject = JSONObject(response)
            val asteroids = parseAsteroidsJsonResult(jsonObject)
            database.asteroidDao.insertAsteroids(asteroids.map { it.toEntity() })
            val today = getFormattedDate(0)
            database.asteroidDao.deleteOldAsteroids(today)

        }
    }

    private suspend fun fetchAsteroidsFromNetwork(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            val response: String = NasaAsteroidApi.retrofitService.getAsteroids().await()
            val jsonObject = JSONObject(response)
            parseAsteroidsJsonResult(jsonObject)
        }
    }

    private suspend fun saveAsteroidToDatabase(asteroids: List<Asteroid>) {
        database.asteroidDao.insertAsteroids(asteroids.map { it.toEntity() })
    }

    override suspend fun getImageOfTheDay(): PictureDay {
        return withContext(Dispatchers.IO) {
            NasaAsteroidApi.retrofitService.getPictureOfTheDay()
        }
    }

    override suspend fun getAsteroid(id: Long): Asteroid {
        return withContext(Dispatchers.IO) {
            database.asteroidDao.getAsteroidById(id).toDomainModel()
        }
    }

    suspend fun getTodayAsteroids(): List<Asteroid> {
        val today = getFormattedDate(0)
        return withContext(Dispatchers.IO) {
            database.asteroidDao.getAsteroidsFromDate(today).map { it.toDomainModel() }
        }
    }

    suspend fun getSavedAsteroids(): List<Asteroid> {
        return withContext(Dispatchers.IO) {
            database.asteroidDao.getAllAsteroids().map { it.toDomainModel() }
        }
    }

    suspend fun getWeekAsteroids(): List<Asteroid> {
        val today = getFormattedDate(0)
        val endOfWeek = getFormattedDate(6)
        return withContext(Dispatchers.IO) {
            database.asteroidDao.getAsteroidsFromDateRange(today, endOfWeek)
                .map { it.toDomainModel() }
        }
    }


    private fun getFormattedDate(daysFromToday: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysFromToday)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}