package com.example.eatbook.ui.tables.booking.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.eatbook.R
import com.example.eatbook.ui.tables.booking.list.model.Hour

class HourAdapter(
    private val itemHandler: HourItemHandler
): ListAdapter<Hour, HourHolder>(
    hourCallBack
) {

    interface HourItemHandler {
        fun onClick(titleHour: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder =
        HourHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_item_hour, parent, false)
        )

    override fun onBindViewHolder(holder: HourHolder, position: Int) {
        holder.bind(getItem(position), itemHandler)
    }
}

private val hourCallBack = object : DiffUtil.ItemCallback<Hour>() {

    override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
        return oldItem == newItem
    }
}