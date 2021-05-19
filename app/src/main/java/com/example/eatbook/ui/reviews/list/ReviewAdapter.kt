package com.example.eatbook.ui.reviews.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.eatbook.R
import com.example.eatbook.ui.reviews.list.model.ReviewList

class ReviewAdapter(
    private val itemHandler: ReviewItemHandler
) : ListAdapter<ReviewList, ReviewHolder>(reviewCallback) {

    interface ReviewItemHandler {
        fun onItemClick(idReview: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder =
        ReviewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_review, parent, false)
        )

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.bind(getItem(position), itemHandler)
    }
}

private val reviewCallback = object : DiffUtil.ItemCallback<ReviewList>() {

    override fun areItemsTheSame(oldItem: ReviewList, newItem: ReviewList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewList, newItem: ReviewList): Boolean {
        return oldItem == newItem
    }
}