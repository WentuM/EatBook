package com.example.eatbook.ui.sales.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Sale
import com.example.eatbook.ui.sales.list.model.SaleListModel
import kotlinx.android.synthetic.main.cardview_item_sale.view.*

class SaleHolder(containerView: View):
        RecyclerView.ViewHolder(containerView)  {

    fun bind(sale: SaleListModel, itemHandler: SaleAdapter.SaleItemHandler) {
        with(itemView) {
            sale_title.text = sale.title
            sale_rest_name.text = sale.titleRestaurant
            Glide.with(itemView).load(sale.imageSale).into(sale_image)
            setOnClickListener {
                itemHandler.onClick(sale.id)
            }
        }

    }
}