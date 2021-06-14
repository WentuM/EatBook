package com.example.eatbook.ui.profile.list.booktable

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatbook.ui.profile.list.booktable.model.MyBookTableItemModel
import kotlinx.android.synthetic.main.cardview_item_my_book_table.view.*

class MyBookTableHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    @SuppressLint("SetTextI18n")
    fun bind(
        myBookTableItemModel: MyBookTableItemModel,
        itemHandler: MyBookTableAdapter.MyBookTableItemHandler
    ) {
        with(itemView) {
            txv_my_table_title.text = myBookTableItemModel.nameTable
            txv_my_rest_title.text = myBookTableItemModel.nameRestaurant
            txv_my_count_people.text = "Количество человек: " + myBookTableItemModel.countPlaces
            txv_my_date_book.text = myBookTableItemModel.day
            txv_my_time_book.text =
                myBookTableItemModel.time + " на ${myBookTableItemModel.countHour} часа"

            Glide.with(itemView).load(myBookTableItemModel.imageTable).into(imgv_table_image)
            btn_redirect_rest.setOnClickListener {
                itemHandler.onRedirectToRest(myBookTableItemModel.idRestaurant)
            }
            imgv_my_table_delete.setOnClickListener {
                itemHandler.onDeleteMyTable(myBookTableItemModel.id)
            }
        }

    }
}