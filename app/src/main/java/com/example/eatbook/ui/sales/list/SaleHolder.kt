package com.example.eatbook.ui.sales.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Sale
import kotlinx.android.extensions.LayoutContainer

class SaleHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(sale: Sale, itemHandler: SaleAdapter.SaleItemHandler) {
        with(itemView) {
//            rest_title.text = restaurant.title
//            rest_description.text = restaurant.description
//            rest_price.text = restaurant.price.toString()
//            Glide.with(containerView).load(restaurant.imageRest).into(rest_image)

            setOnClickListener {
                itemHandler.onClick(sale.id)
//                itemHandler.onFavourite(restaurant.id)
            }
        }

    }
}