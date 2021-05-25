package com.example.eatbook.ui.reviews.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatbook.ui.reviews.list.model.ReviewList
import kotlinx.android.synthetic.main.cardview_item_review.view.*

class ReviewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    fun bind(review: ReviewList, itemHandler: ReviewAdapter.ReviewItemHandler) {
        with(itemView) {
            review_username.text = review.userName
            review_rating.rating = review.rating.toFloat()
            review_date.text = review.dateSend
            txv_review_text.text = review.text
            Glide.with(itemView).load(review.userImage).into(review_image)
        }
    }
}