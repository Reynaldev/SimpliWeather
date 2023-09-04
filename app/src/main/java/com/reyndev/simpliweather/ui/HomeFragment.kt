package com.reyndev.simpliweather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.reyndev.simpliweather.data.WeatherModel
import com.reyndev.simpliweather.data.WeatherViewModel
import com.reyndev.simpliweather.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.homeFragment = this

        viewModel.weather.observe(viewLifecycleOwner) {
            setWeatherTemp(it.current.temp, binding.temp)
            setWeatherTemp(it.current.feelsLike, binding.tempFeel)
            setWeatherHumidity(it.current.humidity)
        }

        return binding.root
    }

    private fun setWeatherTemp(text: String, tv: TextView) {
        val strb = StringBuilder(text).also {
            it.indexOf('.').let { c -> it.deleteRange(c, it.length) }
            it.append("°C")
        }

        tv.text = strb
    }

    private fun setWeatherHumidity(text: String) {
        val strb = StringBuilder(text).also {
            it.append("%")
        }

        binding.humidity.text = strb
    }
}