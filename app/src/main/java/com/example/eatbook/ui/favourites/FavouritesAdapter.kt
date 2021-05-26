package com.example.eatbook.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.model.Restaurant
import com.example.eatbook.R
import com.example.eatbook.ui.favourites.model.FavouritesListModel

class FavouritesAdapter(
    private val itemHandler: FavouritesItemHandler
) : ListAdapter<FavouritesListModel, FavouritesHolder>(favouritesCallback) {

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

private val favouritesCallback = object : DiffUtil.ItemCallback<FavouritesListModel>() {

    override fun areItemsTheSame(oldItem: FavouritesListModel, newItem: FavouritesListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavouritesListModel, newItem: FavouritesListModel): Boolean {
        return oldItem == newItem
    }
}