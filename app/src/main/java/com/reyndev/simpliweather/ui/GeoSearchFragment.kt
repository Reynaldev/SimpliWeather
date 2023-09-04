package com.reyndev.simpliweather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.reyndev.simpliweather.adapter.GeoAdapter
import com.reyndev.simpliweather.adapter.GeoListener
import com.reyndev.simpliweather.data.WeatherViewModel
import com.reyndev.simpliweather.databinding.FragmentGeoSearchBinding

class GeoSearchFragment : Fragment() {
    private lateinit var binding: FragmentGeoSearchBinding

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeoSearchBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.geoSearchFragment = this
        binding.viewModel = viewModel

        binding.recyclerView.adapter = GeoAdapter(GeoListener {
//            Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
            viewModel.setGeo(it)
            goBack()
        })

        binding.searchInput.requestFocus()

        return binding.root
    }

    fun goBack() {
        findNavController().navigate(
            GeoSearchFragmentDirections.actionGeoSearchFragmentToHomeFragment()
        )
    }

    fun searchInput() {
//        Log.v("GeoSearchFragment", binding.searchInput.text.toString())
        if (binding.searchInput.text.isNullOrBlank()) {
            binding.searchInput.error = "Please insert a location"
            return
        }

        viewModel.getGeoList(binding.searchInput.text.toString())
    }
}