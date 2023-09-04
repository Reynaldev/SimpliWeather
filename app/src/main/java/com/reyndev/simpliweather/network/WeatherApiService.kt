package com.reyndev.simpliweather.network

import com.reyndev.simpliweather.data.GeoModel
import com.reyndev.simpliweather.data.WeatherModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val GEO_URL = "https://api.openweathermap.org/geo/1.0/"
private const val WEATHER_URL = "https://api.openweathermap.org/data/3.0/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retroGeo = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(GEO_URL)
    .build()

val retroWeather = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(WEATHER_URL)
    .build()

interface GeoApi {
    @GET("direct")
    suspend fun getGeo(
        @Query("q") name: String,
        @Query("limit") limit: Int,
        @Query("appid") id: String
    ): List<GeoModel>
}

interface WeatherApi {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("exclude") exclude: Array<String>,
        @Query("appid") id: String
    ): WeatherModel
}

object WeatherApiService {
    val geoService: GeoApi by lazy {
        retroGeo.create(GeoApi::class.java)
    }

    val weatherService: WeatherApi by lazy {
        retroWeather.create(WeatherApi::class.java)
    }
}