package com.example.climafy.presentation.ui.components

import com.example.climafy.R
import java.util.Locale

fun getWeatherIcon(temperature: Double, description: String): Int {
    val desc = description.lowercase(Locale.getDefault())

    return when {

        "storm" in desc || "thunder" in desc -> R.drawable.storm
        "rain" in desc || "shower" in desc -> R.drawable.rain
        "snow" in desc -> R.drawable.snowy
        "wind" in desc -> R.drawable.windy
        "cloudy" in desc && "sunny" in desc -> R.drawable.cloudy_sunny
        "cloud" in desc -> R.drawable.cloudy
        "sun" in desc || "clear" in desc -> R.drawable.sunny


        temperature < 0 -> R.drawable.snowy
        temperature in 0.0..10.0 -> R.drawable.cloudy
        temperature in 11.0..20.0 -> R.drawable.cloudy_sunny
        temperature in 21.0..30.0 -> R.drawable.sunny
        temperature > 30 -> R.drawable.sunny

        else -> R.drawable.cloudy_sunny
    }
}