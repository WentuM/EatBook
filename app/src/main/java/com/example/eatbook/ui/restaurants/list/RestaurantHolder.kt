package com.example.eatbook.ui.restaurants.list


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Restaurant
import kotlinx.android.synthetic.main.cardview_item_restaurant.view.*

class RestaurantHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    fun bind(restaurant: Restaurant, itemHandler: RestaurantAdapter.RestItemHandler) {
        with(itemView) {
            //другой листенер
            rest_title.text = restaurant.title
            rest_description.text = restaurant.description
            rest_price.text = restaurant.price.toString()
            Glide.with(itemView).load(restaurant.imageRest).into(rest_image)
            setOnClickListener {
                itemHandler.onItemClick(restaurant.id)
            }
            btn_rest_favourite.setOnClickListener {
//                itemHandler.onClick(restaurant.id)
            }
        }

    }
}
