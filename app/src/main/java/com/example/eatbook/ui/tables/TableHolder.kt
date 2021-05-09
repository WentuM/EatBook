package com.example.eatbook.ui.tables

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Table
import kotlinx.android.extensions.LayoutContainer

class TableHolder(override val containerView: View):
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(table: Table, itemHandler: TableAdapter.TableItemHandler) {
        with(itemView) {
//            rest_title.text = restaurant.title
//            rest_description.text = restaurant.description
//            rest_price.text = restaurant.price.toString()
//            Glide.with(containerView).load(restaurant.imageRest).into(rest_image)

            setOnClickListener {
                itemHandler.onClick(table.id)
//                itemHandler.onFavourite(restaurant.id)
            }
        }

    }
}