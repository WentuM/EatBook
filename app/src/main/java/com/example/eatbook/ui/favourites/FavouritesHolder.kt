package com.example.eatbook.ui.favourites

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Restaurant
import com.example.eatbook.R
import com.example.eatbook.ui.favourites.model.FavouritesListModel
import kotlinx.android.synthetic.main.cardview_item_restaurant.view.*

class FavouritesHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    @SuppressLint("SetTextI18n")
    fun bind(restaurant: FavouritesListModel, itemHandler: FavouritesAdapter.FavouritesItemHandler) {
        with(itemView) {
            rest_title.text = restaurant.title
            rest_description.text = restaurant.address
            rest_price.text = restaurant.price.toString() + " ₽"
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
                itemHandler.onFavourite(restaurant)
            }
        }

    }
}