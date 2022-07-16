package com.example.bikeapitest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bikeapitest.databinding.ItemBikesApiBinding
import com.example.bikeapitest.model.Feature

class BikesApiAdapter(
    private val fetchApi: ArrayList<Feature>,
    private val openMapClick: OpenMapClickListener
) : ListAdapter<Feature, BikesApiAdapter.DataViewHolder>(DIFF_CALLBACK) {

    class DataViewHolder(private val itemBinding: ItemBikesApiBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Feature, openMapClick: OpenMapClickListener) {
            itemBinding.placesTextView.text = item.properties.label
            itemBinding.distanceTextView.text = "600 - Bike Station"
            itemBinding.availableBikeTextView.text = item.properties.bikes
            itemBinding.availablePlaceTextView.text = item.properties.bikeRacks

            itemBinding.cardView.setOnClickListener {
                openMapClick.openMapClickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val itemBinding = ItemBikesApiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(fetchApi[position], openMapClick)
    }

    fun addData(list: List<Feature>) {
        fetchApi.addAll(list)
    }

    interface OpenMapClickListener {
        fun openMapClickListener(feature: Feature)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Feature> = object : DiffUtil.ItemCallback<Feature>() {
            override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean {
                return oldItem == newItem
            }
        }
    }
}
