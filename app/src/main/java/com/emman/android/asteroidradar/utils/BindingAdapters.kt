package com.emman.android.asteroidradar.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.emman.android.asteroidradar.R
import com.emman.android.asteroidradar.presentation.viewmodels.NasaApiStatus
import com.squareup.picasso.Picasso

@BindingAdapter("nasaApiStatus")
fun bindStatus(statusProgressBar: ProgressBar, status: NasaApiStatus) {
    when (status) {
        NasaApiStatus.LOADING -> {
            statusProgressBar.visibility = View.VISIBLE
        }

        NasaApiStatus.DONE -> {
            statusProgressBar.visibility = View.GONE
        }

        NasaApiStatus.ERROR -> {
            statusProgressBar.visibility = View.GONE
        }
    }
}


@BindingAdapter("imageTextStatus")
fun bindImageText(imageText: View, status: NasaApiStatus?) {
    when (status) {
        NasaApiStatus.DONE -> {
            imageText.visibility = View.VISIBLE
        }

        else -> {
            imageText.visibility = View.GONE
        }
    }
}

@BindingAdapter("recyclerStatus")
fun bindRecyclerView(recyclerView: View, status: NasaApiStatus) {
    when (status) {
        NasaApiStatus.LOADING, NasaApiStatus.DONE -> {
            recyclerView.visibility = View.VISIBLE
        }

        NasaApiStatus.ERROR -> {
            recyclerView.visibility = View.GONE
        }

    }
}

@BindingAdapter("iconStatus")
fun ImageView.bindAsteroidStatusImage(isHazardous: Boolean) {
    if (isHazardous) {
        setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("pictureOfDay")
fun ImageView.bindPictureOfDay(pictureDayUrl: String?) {
    if (pictureDayUrl != null) {
        Picasso.get()
            .load(pictureDayUrl)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .error(R.drawable.ic_broken_image)
            .into(this)
    } else {
        setImageResource(R.drawable.placeholder_picture_of_day)
    }
}

@BindingAdapter("asteroidStatusImage")
fun ImageView.bindAsteroidImage(isHazardous: Boolean) {
    if (isHazardous) {
        setImageResource(R.drawable.asteroid_hazardous)
    } else {
        setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}