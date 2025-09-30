package com.emman.android.asteroidradar.data.remote

import com.emman.android.asteroidradar.BuildConfig
import com.emman.android.asteroidradar.domain.models.PictureDay
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BuildConfig.BASE_URL)
    .build()

interface NasaAsteroidService {
    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): Call<String>

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): PictureDay
}


object NasaAsteroidApi {
    val retrofitService: NasaAsteroidService by lazy {
        retrofit.create(NasaAsteroidService::class.java)
    }

}