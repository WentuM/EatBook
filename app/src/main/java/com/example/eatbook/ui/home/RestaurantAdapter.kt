package com.example.eatbook.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Restaurant
import com.example.eatbook.R

class RestaurantAdapter(var list: ArrayList<Restaurant>, private val itemHandler: RestItemHandler) :
    RecyclerView.Adapter<RestaurantHolder>() {

    interface RestItemHandler {
        fun onClick(idRestaurant: String)
        fun onFavourite(idRestaurant: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder =
        RestaurantHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_item_restaurant, parent, false)
        )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        holder.bind(list[position], itemHandler)
    }
}