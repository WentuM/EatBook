package com.example.eatbook.ui.restaurants.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.model.Restaurant
import com.example.eatbook.R

class RestaurantAdapter(
    private val itemHandler: RestItemHandler
) : ListAdapter<Restaurant, RestaurantHolder>(
    userCallback
) {

    interface RestItemHandler {
        fun onClick(idRestaurant: String)
        fun onFavourite(idRestaurant: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder =
        RestaurantHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_item_restaurant, parent, false)
        )

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        holder.bind(getItem(position), itemHandler)
    }
}

private val userCallback = object : DiffUtil.ItemCallback<Restaurant>() {

    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem == newItem
    }
}