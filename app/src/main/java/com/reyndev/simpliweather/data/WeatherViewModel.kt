package com.reyndev.simpliweather.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reyndev.simpliweather.network.WeatherApiService
import kotlinx.coroutines.launch
import java.lang.StringBuilder

enum class WeatherApiStatus { LOADING, DONE, ERROR }

class WeatherViewModel : ViewModel() {
    private val _status = MutableLiveData<WeatherApiStatus>()
    val status: LiveData<WeatherApiStatus> = _status

    private val _geo = MutableLiveData<List<GeoModel>>()
    val geo: LiveData<List<GeoModel>> = _geo

    private val _location = MutableLiveData<GeoModel>()
    val location: LiveData<GeoModel> = _location

    private val _weather = MutableLiveData<WeatherModel>()
    val weather: LiveData<WeatherModel> = _weather

    init {
        // For startup, rather than making a new call to the API
        val newGeo = GeoModel(
            "Special Capital Region of Jakarta",
            (-6.1753942).toString(),
            (106.827183).toString()
        )

        _location.value = newGeo

        getWeatherData()
    }

    fun getGeoList() {
        viewModelScope.launch {
            try {
                _geo.value = WeatherApiService.geoService.getGeo()
                _location.value = _geo.value!![0]

                Log.i("WeatherViewModel", geo.value!![0].toString())
            } catch (e: Exception) {
                Log.wtf("WeatherViewModel", "Failed to retrieve geo\n${e.message}")
            }
        }
    }

    fun getWeatherData() {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING

            try {
                _weather.value = WeatherApiService.weatherService.getWeather(
                    location.value!!.lat.toDouble(),
                    location.value!!.lon.toDouble(),
                    "metric",
                    arrayOf(
                        "minutely",
                        "hourly",
                        "daily",
                        "alerts"
                    ),
                    "18ccbbd129b7bdecaaf072a9f9977f01"
                )
                _status.value = WeatherApiStatus.DONE

                Log.i("WeatherViewModel", weather.value!!.toString())
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR

                Log.wtf("WeatherViewModel", "Failed to retrieve weather\n${e.message}")
            }
        }
    }
}