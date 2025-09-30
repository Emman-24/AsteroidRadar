package com.emman.android.asteroidradar.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emman.android.asteroidradar.data.local.getDatabase
import com.emman.android.asteroidradar.data.repository.AsteroidRepositoryImpl
import com.emman.android.asteroidradar.domain.models.Asteroid
import com.emman.android.asteroidradar.domain.models.PictureDay
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repository: AsteroidRepositoryImpl = AsteroidRepositoryImpl(database)

    private val _status = MutableLiveData<NasaApiStatus?>()
    val status: LiveData<NasaApiStatus?> = _status

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> = _asteroids

    private var _pictureOfTheDay = MutableLiveData<PictureDay?>()
    val pictureOfTheDay: LiveData<PictureDay?> = _pictureOfTheDay

    private var _asteroid = MutableLiveData<Asteroid>()
    val asteroid: LiveData<Asteroid> = _asteroid

    fun loadData() {
        viewModelScope.launch {
            _status.value = NasaApiStatus.LOADING
            try {
                val asteroidList = repository.getAsteroids()
                _asteroids.value = asteroidList
                _status.value = NasaApiStatus.DONE
            } catch (e: Exception) {
                _asteroids.value = emptyList()
                _status.value = NasaApiStatus.ERROR
            }
        }
    }


    fun loadImageOfTheDay() {
        viewModelScope.launch {
            _status.value = NasaApiStatus.LOADING
            try {
                val pictureDay = repository.getImageOfTheDay()
                _pictureOfTheDay.value = pictureDay
                _status.value = NasaApiStatus.DONE
            } catch (e: Exception) {
                _pictureOfTheDay.value = null
                _status.value = NasaApiStatus.ERROR
            }
        }
    }

    fun getAsteroid(id: Long) {
        viewModelScope.launch {
            _asteroid.value = repository.getAsteroid(id)
        }
    }

}


enum class NasaApiStatus { LOADING, ERROR, DONE }