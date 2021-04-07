package com.example.eatbook.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Restaurant
import com.example.eatbook.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cardview_item_restaurant.*

class RestaurantHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(restaurant: Restaurant, itemHandler: RestaurantAdapter.RestItemHandler) {
        with(itemView) {
            rest_title.text = restaurant.title
            rest_description.text = restaurant.description
            rest_price.text = restaurant.price.toString()
            Glide.with(containerView).load(restaurant.imageRest).into(rest_image)

            setOnClickListener {
                itemHandler.onClick(restaurant.id)
                itemHandler.onFavourite(restaurant.id)
            }
        }

    }

//    companion object {
//        fun create(parent: ViewGroup, itemClick: (Int) -> Unit, favouriteClick: (Int) -> Unit): RestaurantHolder =
//            RestaurantHolder(
//                LayoutInflater.from(parent.context).inflate(
//                    R.layout.cardview_item_restaurant,
//                    parent,
//                    false
//                ),
//                itemClick,
//                favouriteClick
//            )
//    }

}
