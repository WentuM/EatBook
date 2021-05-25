package com.example.eatbook.ui.profile.list.booktable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.eatbook.R
import com.example.eatbook.ui.profile.list.booktable.model.MyBookTableItemModel
import com.example.eatbook.ui.restaurants.list.RestaurantHolder

class MyBookTableAdapter(
    private val itemHandler: MyBookTableItemHandler
) : ListAdapter<MyBookTableItemModel, MyBookTableHolder>(myBookTableCallback) {

    interface MyBookTableItemHandler {
        fun onRedirectToRest(idRestaurant: String)
        fun onDeleteMyTable(idBookTable: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookTableHolder =
        MyBookTableHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_my_book_table, parent, false)
        )

    override fun onBindViewHolder(holder: MyBookTableHolder, position: Int) {
        holder.bind(getItem(position), itemHandler)
    }
}

private val myBookTableCallback = object : DiffUtil.ItemCallback<MyBookTableItemModel>() {

    override fun areItemsTheSame(oldItem: MyBookTableItemModel, newItem: MyBookTableItemModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MyBookTableItemModel, newItem: MyBookTableItemModel): Boolean {
        return oldItem == newItem
    }
}