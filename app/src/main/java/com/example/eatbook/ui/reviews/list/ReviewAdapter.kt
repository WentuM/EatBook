package com.example.eatbook.ui.reviews.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.eatbook.R
import com.example.eatbook.ui.reviews.list.model.ReviewListModel

class ReviewAdapter(
    private val itemHandler: ReviewItemHandler
) : ListAdapter<ReviewListModel, ReviewHolder>(reviewCallback) {

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

private val reviewCallback = object : DiffUtil.ItemCallback<ReviewListModel>() {

    override fun areItemsTheSame(oldItem: ReviewListModel, newItem: ReviewListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewListModel, newItem: ReviewListModel): Boolean {
        return oldItem == newItem
    }
}