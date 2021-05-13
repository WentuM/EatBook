package com.example.eatbook.ui.restaurants.reviews

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Review
import kotlinx.android.synthetic.main.cardview_item_review.*

class ReviewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    fun bind(review: Review, itemHandler: ReviewAdapter.ReviewItemHandler) {
        with(itemView) {
            //другой листенер
//            rest_title.text = review.title
//            rest_description.text = restaurant.description
//            rest_price.text = restaurant.price.toString()
//            Glide.with(itemView).load(restaurant.imageRest).into(rest_image)
//            setOnClickListener {
//                itemHandler.onItemClick(restaurant.id)
//            }
//
//            btn_rest_favourite.setOnClickListener {
////                itemHandler.onClick(restaurant.id)
//            }
        }
    }
}