package com.reyndev.simpliweather.data

import com.squareup.moshi.Json

data class GeoModel(
    val name: String,
    val lat: String,
    val lon: String
)

data class WeatherModel(
    val timezone: String,
    val current: Current
)

data class Current(
    val temp: String,
    @Json(name="feels_like") val feelsLike: String,
    val pressure: String,
    val humidity: String,
    val uvi: String,
    @Json(name="wind_speed") val windSpeed: String,
    @Json(name="wind_deg") val windDirection: String,
    val weather: List<Weather>
)

data class Weather(
    val id: String,
    val main: String,
    val description: String,
    val icon: String
)