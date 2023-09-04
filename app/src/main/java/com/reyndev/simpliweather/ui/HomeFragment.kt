package com.reyndev.simpliweather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.reyndev.simpliweather.data.WeatherApiStatus
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

        viewModel.status.observe(viewLifecycleOwner) {
            if (it == WeatherApiStatus.ERROR)
                showErrorDialog()
        }

        viewModel.weather.observe(viewLifecycleOwner) {
            setWeatherTemp(it.current.temp, binding.temp)
            setWeatherTemp(it.current.feelsLike, binding.tempFeel)
            setWeatherHumidity(it.current.humidity)
        }

        return binding.root
    }

    private fun showErrorDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage("Oops! I can't retrieve weather data\nCheck out your internet connection.")
            .setPositiveButton("Retry") { dialog, _ ->
                viewModel.getWeatherData()
                dialog.dismiss()
            }
            .setNegativeButton("Exit") { _, _ ->
                activity?.finish()
            }
            .show()
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

    fun goToSearchFragment() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToGeoSearchFragment()
        )
    }

    fun appInfo() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("About SimpliWeather")
            .setMessage("This app was made by ReynDev")
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}