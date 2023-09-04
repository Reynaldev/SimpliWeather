package com.reyndev.simpliweather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reyndev.simpliweather.data.GeoModel
import com.reyndev.simpliweather.databinding.GeoItemListBinding

class GeoAdapter(private val clickListener: GeoListener)
    : ListAdapter<GeoModel, GeoAdapter.GeoViewHolder>(DiffCallback) {

    class GeoViewHolder(private var binding: GeoItemListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: GeoListener, geo: GeoModel) {
            if (!geo.state.isNullOrBlank())
                binding.geoName.text = "${geo.name}, ${geo.state}"
            else
                binding.geoName.text = geo.name

            binding.geo = geo
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return GeoViewHolder(
            GeoItemListBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GeoViewHolder, position: Int) {
        val geo = getItem(position)

        holder.bind(clickListener, geo)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<GeoModel>() {
        override fun areItemsTheSame(oldItem: GeoModel, newItem: GeoModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: GeoModel, newItem: GeoModel): Boolean {
            return oldItem.state == newItem.state
        }
    }
}

class GeoListener(val clickListener: (geo: GeoModel) -> Unit) {
    fun onClick(geo: GeoModel) = clickListener(geo)
}