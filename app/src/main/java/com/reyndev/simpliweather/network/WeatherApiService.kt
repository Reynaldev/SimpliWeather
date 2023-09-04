package com.reyndev.simpliweather.network

import com.reyndev.simpliweather.data.GeoModel
import com.reyndev.simpliweather.data.WeatherModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApi {
    @GET("geo/1.0/direct")
    suspend fun getGeo(
        @Query("q") name: String,
        @Query("limit") limit: Int,
        @Query("appid") id: String
    ): List<GeoModel>

    @GET("data/3.0/onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("exclude") exclude: Array<String>,
        @Query("appid") id: String
    ): WeatherModel
}

object WeatherApiService {
    val weatherService: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}