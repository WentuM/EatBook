package com.example.eatbook.ui.profile.list.booktable

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Restaurant
import com.example.eatbook.R
import com.example.eatbook.ui.profile.list.booktable.model.MyBookTableItemModel
import com.example.eatbook.ui.restaurants.list.RestaurantAdapter
import kotlinx.android.synthetic.main.cardview_item_my_book_table.view.*
import kotlinx.android.synthetic.main.cardview_item_restaurant.view.*

class MyBookTableHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    @SuppressLint("SetTextI18n")
    fun bind(myBookTableItemModel: MyBookTableItemModel, itemHandler: MyBookTableAdapter.MyBookTableItemHandler) {
        with(itemView) {
            txv_my_table_title.text = myBookTableItemModel.nameTable
            txv_my_rest_title.text = myBookTableItemModel.nameRestaurant
            txv_my_count_people.text = "Количество человек: "
            txv_my_date_book.text = myBookTableItemModel.day
            txv_my_time_book.text = myBookTableItemModel.time + " на ${myBookTableItemModel.countHour} часа"

//            rest_title.text = restaurant.title
//            rest_description.text = restaurant.description
//            rest_price.text = restaurant.price.toString()
            Glide.with(itemView).load(myBookTableItemModel.imageTable).into(imgv_table_image)
//            setOnClickListener {
//                itemHandler.onItemClick(restaurant.id)
//            }
//            if (restaurant.likeRest == 1) {
//                btn_rest_favourite.setBackgroundResource(R.drawable.ic_baseline_favorite_24_red)
//            } else {
//                btn_rest_favourite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
//            }
//            btn_rest_favourite.setOnClickListener {
//                itemHandler.onFavourite(restaurant.id)
//            }
        }

    }
}