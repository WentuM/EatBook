package com.example.eatbook.ui.tables.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatbook.ui.tables.list.model.TableListModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cardview_item_table.*

class TableHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(table: TableListModel, itemHandler: TableAdapter.TableItemHandler) {
        with(itemView) {
            table_title.text = table.title
            table_count.text = table.countPlaces.toString()
            Glide.with(containerView).load(table.image).into(table_image)

            setOnClickListener {
                itemHandler.onClick(table.id)
            }
        }

    }
}