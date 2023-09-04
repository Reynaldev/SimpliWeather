package com.reyndev.simpliweather

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.reyndev.simpliweather.adapter.GeoAdapter
import com.reyndev.simpliweather.data.GeoModel
import com.reyndev.simpliweather.data.WeatherApiStatus

@BindingAdapter("setText")
fun setText(textView: TextView, text: String?) {
    textView.text = text
}

@BindingAdapter("geoData")
fun geoData(recyclerView: RecyclerView, data: List<GeoModel>?) {
    val adapter = recyclerView.adapter as GeoAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiStatus")
fun apiStatus(imageView: ImageView, status: WeatherApiStatus) {
    when (status) {
        WeatherApiStatus.LOADING -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.loading_animation)
        }
        WeatherApiStatus.DONE -> {
            imageView.visibility = View.GONE
        }
        WeatherApiStatus.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.ic_error)
        }
    }
}