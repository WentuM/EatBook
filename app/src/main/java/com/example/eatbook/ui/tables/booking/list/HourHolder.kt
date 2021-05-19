package com.example.eatbook.ui.tables.booking.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.eatbook.R
import com.example.eatbook.ui.tables.booking.list.model.Hour
import kotlinx.android.synthetic.main.cardview_item_hour.view.*

class HourHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {

    @SuppressLint("ResourceAsColor")
    fun bind(hour: Hour, itemHandler: HourAdapter.HourItemHandler) {
        with(itemView) {
            choose_hour.text = hour.title
            if (hour.exist) {
                choose_hour.setOnClickListener {
                    itemHandler.onClick(hour.title)
                    Log.d("qweHour", "cool")
                }
            } else {
                choose_hour.setBackgroundColor(R.color.colorBtnProfile2)
            }
        }

    }
}