package com.emman.android.asteroidradar.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emman.android.asteroidradar.domain.models.Asteroid

@Entity(tableName = "asteroids")
data class AsteroidEntity(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val potentiallyHazardous: Boolean,
)

fun AsteroidEntity.toDomainModel(): Asteroid {
    return Asteroid(
        id = id,
        codename = codename,
        closeApproachDate = closeApproachDate,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter,
        relativeVelocity = relativeVelocity,
        distanceFromEarth = distanceFromEarth,
        potentiallyHazardous = potentiallyHazardous,
    )
}

fun Asteroid.toEntity(): AsteroidEntity {
    return AsteroidEntity(
        id = id,
        codename = codename,
        closeApproachDate = closeApproachDate,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter,
        relativeVelocity = relativeVelocity,
        distanceFromEarth = distanceFromEarth,
        potentiallyHazardous = potentiallyHazardous,
    )
}