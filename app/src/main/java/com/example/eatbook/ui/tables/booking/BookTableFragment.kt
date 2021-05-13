package com.example.eatbook.ui.tables.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.restaurants.detail.RestaurantDetailViewModel
import kotlinx.android.synthetic.main.fragment_book_rest.*
import javax.inject.Inject

class BookTableFragment: Fragment() {

    @Inject
    lateinit var bookTableViewModel: BookTableViewModel
    private var idTable: String = ""
    private var countHours: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("idTable")?.let {
            idTable = it
        }
//        arguments?.getString("idRestaurant")?.let {
//            idTable = it
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.tableListComponentFactory()
            .create(this)
            .inject(this)
        val root = inflater.inflate(R.layout.fragment_book_rest, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initClicks()
    }

    private fun initFields() {
        txv_book_number_people.text = countHours.toString()
    }

    private fun initClicks() {
        imgv_book_hours_minus.setOnClickListener {
            if (countHours == 1) {
                Toast.makeText(activity, "Минимальное время бронирования - 1 час", Toast.LENGTH_SHORT).show()
            } else {
                countHours--
                txv_book_number_people.text = countHours.toString()
            }
        }
        imgv_book_hours_plus.setOnClickListener {
            if (countHours == 8) {
                Toast.makeText(activity, "Максимальное время бронирования - 8 часов", Toast.LENGTH_SHORT).show()
            } else {
                countHours++
                txv_book_number_people.text = countHours.toString()
            }
        }
    }
}