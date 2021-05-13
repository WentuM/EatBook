package com.example.eatbook.ui.restaurants.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.model.Review
import com.example.eatbook.R

class ReviewAdapter(
    private val itemHandler: ReviewItemHandler
) : ListAdapter<Review, ReviewHolder>(reviewCallback) {

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

private val reviewCallback = object : DiffUtil.ItemCallback<Review>() {

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}