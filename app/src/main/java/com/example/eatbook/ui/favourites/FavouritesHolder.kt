package com.example.eatbook.ui.favourites

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Restaurant
import com.example.eatbook.R
import kotlinx.android.synthetic.main.cardview_item_restaurant.view.*

class FavouritesHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    fun bind(restaurant: Restaurant, itemHandler: FavouritesAdapter.FavouritesItemHandler) {
        with(itemView) {
            rest_title.text = restaurant.title
            rest_description.text = restaurant.description
            rest_price.text = restaurant.price.toString()
            Glide.with(itemView).load(restaurant.imageRest).into(rest_image)
            setOnClickListener {
                itemHandler.onItemClick(restaurant.id)
            }
            if (restaurant.likeRest == 1) {
                btn_rest_favourite.setBackgroundResource(R.drawable.ic_baseline_favorite_24_red)
            } else {
                btn_rest_favourite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
            }
            btn_rest_favourite.setOnClickListener {
                itemHandler.onFavourite(restaurant.id, restaurant.likeRest)
            }
        }

    }
}