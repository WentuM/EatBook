package com.example.eatbook.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.model.Restaurant
import com.example.eatbook.R

class FavouritesAdapter(
    private val itemHandler: FavouritesItemHandler
) : ListAdapter<Restaurant, FavouritesHolder>(favouritesCallback) {

    interface FavouritesItemHandler {
        fun onItemClick(idRestaurant: String)
        fun onFavourite(idRestaurant: String, likeRest: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesHolder =
        FavouritesHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_restaurant, parent, false)
        )

    override fun onBindViewHolder(holder: FavouritesHolder, position: Int) {
        holder.bind(getItem(position), itemHandler)
    }
}

private val favouritesCallback = object : DiffUtil.ItemCallback<Restaurant>() {

    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
        return oldItem == newItem
    }
}