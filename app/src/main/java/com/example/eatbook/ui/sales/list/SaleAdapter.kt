package com.example.eatbook.ui.sales.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.Sale
import com.example.eatbook.R
import com.example.eatbook.ui.sales.list.model.SaleListModel

class SaleAdapter(
    private val itemHandler: SaleItemHandler
): ListAdapter<SaleListModel, SaleHolder>(
    saleCallback
) {

    interface SaleItemHandler {
        fun onClick(idSale: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleHolder =
        SaleHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_item_sale, parent, false)
        )

    override fun onBindViewHolder(holder: SaleHolder, position: Int) {
        holder.bind(getItem(position), itemHandler)
    }
}

private val saleCallback = object : DiffUtil.ItemCallback<SaleListModel>() {

    override fun areItemsTheSame(oldItem: SaleListModel, newItem: SaleListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SaleListModel, newItem: SaleListModel): Boolean {
        return oldItem == newItem
    }
}