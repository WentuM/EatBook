package com.example.eatbook.ui.restaurants.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.model.Restaurant
import com.example.eatbook.R
import com.example.eatbook.ui.restaurants.list.model.RestaurantListModel

class RestaurantAdapter(
    private val itemHandler: RestItemHandler
) : ListAdapter<RestaurantListModel, RestaurantHolder>(restCallback) {

    interface RestItemHandler {
        fun onItemClick(idRestaurant: String)
        fun onFavourite(restaurantListModel: RestaurantListModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder =
        RestaurantHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_item_restaurant, parent, false)
        )

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        holder.bind(getItem(position), itemHandler)
    }
}

private val restCallback = object : DiffUtil.ItemCallback<RestaurantListModel>() {

    override fun areItemsTheSame(oldItem: RestaurantListModel, newItem: RestaurantListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RestaurantListModel, newItem: RestaurantListModel): Boolean {
        return oldItem == newItem
    }
}