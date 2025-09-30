package com.emman.android.asteroidradar.domain.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PictureDay(
    var date: String,
    var explanation: String,
    var hdurl: String,
    @Json(name = "media_type")
    var mediaType: String,
    @Json(name = "service_version")
    var serviceVersion: String,
    var title: String,
    var url: String,
) : Parcelable
