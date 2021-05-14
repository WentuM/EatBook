package com.example.eatbook.ui.restaurants.reviews

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Review
import kotlinx.android.synthetic.main.cardview_item_review.*
import kotlinx.android.synthetic.main.cardview_item_review.view.*
import kotlinx.android.synthetic.main.dialog_review_create.view.*

class ReviewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    fun bind(review: Review, itemHandler: ReviewAdapter.ReviewItemHandler) {
        with(itemView) {
            review_username.text = review.userName
            review_raiting.rating = review.rating.toFloat()
            review_date.text = review.dateSend
            txv_review_text.text = review.text

//            Glide.with(itemView).load(restaurant.imageRest).into(rest_image)
        }
    }
}