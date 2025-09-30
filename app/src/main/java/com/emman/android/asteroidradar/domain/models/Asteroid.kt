package com.emman.android.asteroidradar.domain.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val potentiallyHazardous: Boolean,
) : Parcelable{
    @IgnoredOnParcel
    val descriptionImage = if (potentiallyHazardous) "Image status is potentially hazardous" else "Image status is not hazardous"
}
