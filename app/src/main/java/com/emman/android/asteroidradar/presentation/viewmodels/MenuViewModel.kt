package com.emman.android.asteroidradar.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.emman.android.asteroidradar.data.local.getDatabase
import com.emman.android.asteroidradar.data.repository.AsteroidRepositoryImpl
import com.emman.android.asteroidradar.domain.models.Asteroid
import com.emman.android.asteroidradar.domain.models.PictureDay
import kotlinx.coroutines.launch

class MenuViewModel(
    private val repository: AsteroidRepositoryImpl
) : ViewModel() {

    private val _asteroidStatus = MutableLiveData<NasaApiStatus?>()
    val asteroidStatus: LiveData<NasaApiStatus?> = _asteroidStatus

    private val _imageStatus = MutableLiveData<NasaApiStatus?>()
    val imageStatus: LiveData<NasaApiStatus?> = _imageStatus

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> = _asteroids

    private val _pictureOfTheDay = MutableLiveData<PictureDay?>()
    val pictureOfTheDay: LiveData<PictureDay?> = _pictureOfTheDay

    private val _asteroid = MutableLiveData<Asteroid>()
    val asteroid: LiveData<Asteroid> = _asteroid

    fun loadData() {
        loadAsteroids()
    }

    private fun loadAsteroids() {
        loadAsteroidsWithFilter { repository.getAsteroids() }
    }

    fun loadSavedAsteroids() {
        loadAsteroidsWithFilter { repository.getSavedAsteroids() }
    }

    fun loadTodayAsteroids() {
        loadAsteroidsWithFilter { repository.getTodayAsteroids() }
    }

    fun loadWeekAsteroids() {
        loadAsteroidsWithFilter { repository.getWeekAsteroids() }
    }

    private fun loadAsteroidsWithFilter(loadFunction: suspend () -> List<Asteroid>) {
        viewModelScope.launch {
            _asteroidStatus.value = NasaApiStatus.LOADING
            try {
                val asteroidList = loadFunction()
                _asteroids.value = asteroidList
                _asteroidStatus.value = NasaApiStatus.DONE
            } catch (_: Exception) {
                _asteroids.value = emptyList()
                _asteroidStatus.value = NasaApiStatus.ERROR
            }
        }
    }

    fun loadImageOfTheDay() {
        viewModelScope.launch {
            _imageStatus.value = NasaApiStatus.LOADING
            try {
                val pictureDay = repository.getImageOfTheDay()
                _pictureOfTheDay.value = pictureDay
                _imageStatus.value = NasaApiStatus.DONE
            } catch (_: Exception) {
                _pictureOfTheDay.value = null
                _imageStatus.value = NasaApiStatus.ERROR
            }
        }
    }

    fun getAsteroid(id: Long) {
        viewModelScope.launch {
            try {
                _asteroid.value = repository.getAsteroid(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

class MenuViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            val database = getDatabase(application)
            val repository = AsteroidRepositoryImpl(database)
            return MenuViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


enum class NasaApiStatus { LOADING, ERROR, DONE }