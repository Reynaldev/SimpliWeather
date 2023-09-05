package com.reyndev.simpliweather.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reyndev.simpliweather.network.WeatherApiService
import kotlinx.coroutines.launch

enum class WeatherApiStatus { LOADING, DONE, ERROR }

class WeatherViewModel : ViewModel() {
    private val appId = "18ccbbd129b7bdecaaf072a9f9977f01"

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
            "-6.1753942",
            "106.827183",
            null
        )

        _location.value = newGeo

        getWeatherData()

        Log.i("WeatherViewModel", "WeahterViewModel initialized")
    }

    fun getGeoList(name: String) {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING

            try {
                _geo.value = WeatherApiService.weatherService.getGeo(
                    name,
                    5,
                    appId
                )
                _status.value = WeatherApiStatus.DONE

                Log.i("WeatherViewModel", geo.value!![0].toString())
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR

                Log.w("WeatherViewModel", "Failed to retrieve geo\n${e.message}")
            }
        }
    }

    fun setGeo(geo: GeoModel) {
        _location.value = geo
        getWeatherData()
    }

    fun getWeatherData() {
        viewModelScope.launch {
            _status.value = WeatherApiStatus.LOADING

            try {
                _weather.value = WeatherApiService.weatherService.getWeather(
                    _location.value!!.lat,
                    _location.value!!.lon,
                    "metric",
                    arrayOf(
                        "minutely",
                        "hourly",
                        "daily",
                        "alerts"
                    ),
                    appId
                )
                _status.value = WeatherApiStatus.DONE

                Log.i("WeatherViewModel", _weather.value.toString())
            } catch (e: Exception) {
                _status.value = WeatherApiStatus.ERROR

                Log.w("WeatherViewModel", "Failed to retrieve weather\n${e.message}")
            }
        }
    }
}