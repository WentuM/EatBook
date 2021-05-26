package com.example.eatbook.ui.reviews.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatbook.ui.reviews.list.model.ReviewListModel
import kotlinx.android.synthetic.main.cardview_item_review.view.*

class ReviewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    fun bind(reviewModel: ReviewListModel, itemHandler: ReviewAdapter.ReviewItemHandler) {
        with(itemView) {
            review_username.text = reviewModel.nameUser
            review_rating.rating = reviewModel.rating.toFloat()
            review_date.text = reviewModel.dateSend
            txv_review_text.text = reviewModel.text
            Glide.with(itemView).load(reviewModel.imageUser).into(review_image)
        }
    }
}