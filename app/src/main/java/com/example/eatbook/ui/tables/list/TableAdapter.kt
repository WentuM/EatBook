package com.example.eatbook.ui.tables.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.model.Table
import com.example.eatbook.R
import com.example.eatbook.ui.tables.list.model.TableListModel

class TableAdapter(
    private val itemHandler: TableItemHandler
): ListAdapter<TableListModel, TableHolder>(
    saleCallback
) {

    interface TableItemHandler {
        fun onClick(idTable: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableHolder =
        TableHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_item_table, parent, false)
        )

    override fun onBindViewHolder(holder: TableHolder, position: Int) {
        holder.bind(getItem(position), itemHandler)
    }
}

private val saleCallback = object : DiffUtil.ItemCallback<TableListModel>() {

    override fun areItemsTheSame(oldItem: TableListModel, newItem: TableListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TableListModel, newItem: TableListModel): Boolean {
        return oldItem == newItem
    }
}