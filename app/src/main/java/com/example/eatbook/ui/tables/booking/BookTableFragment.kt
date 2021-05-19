package com.example.eatbook.ui.tables.booking

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.tables.booking.list.HourAdapter
import com.example.eatbook.ui.tables.booking.list.model.BookTableItemModel
import com.example.eatbook.ui.tables.booking.list.model.Hour
import kotlinx.android.synthetic.main.fragment_book_rest.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class BookTableFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, HourAdapter.HourItemHandler {

    @Inject
    lateinit var bookTableViewModel: BookTableViewModel
    private val hourAdapter = HourAdapter(this)
    private var currentListHour = arrayListOf<Hour>()
    private var idTable: String = ""
    private var countHours: Int = 1


    companion object {
        private lateinit var calendar: Calendar
        private lateinit var reviewDialog: AlertDialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("idTable")?.let {
            idTable = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.tableBookComponentFactory()
            .create(this)
            .inject(this)
        initListHour()
        val root = inflater.inflate(R.layout.fragment_book_rest, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hour_list.adapter = hourAdapter
        initFields()
        initClicks()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initFields() {
        calendar = Calendar.getInstance()
        txv_book_number_hour.text = countHours.toString()
        txv_book_date.text = SimpleDateFormat("dd, MMM yyyy").format(calendar.time)
        txv_book_time.text = SimpleDateFormat("HH:mm").format(calendar.time)
    }

    private fun initClicks() {
        imgv_book_date.setOnClickListener {
            showDatePickerDialog()
        }

        imgv_book_time.setOnClickListener {
            bookTableViewModel.getBookTableByDay(txv_book_date.text.toString(), idTable)
            bookTableViewModel.bookTable().observe(viewLifecycleOwner, Observer {
                Log.d("qweLLIST", it.toString())
                var listHour: ArrayList<Hour> = arrayListOf<Hour>()
                for (hour: Hour in currentListHour) {
                    if (it.contains(hour)) {
                        hour.exist = false
                        Log.d("qweHour2", hour.toString())
                    }
                }
                Log.d("qweUCUCU", currentListHour.toString())
                hourAdapter.submitList(currentListHour)
                hour_list.visibility = View.VISIBLE
            })
//            showTimePickerDialog()
//            showReviewDialog()

        }

        imgv_book_hours_minus.setOnClickListener {
            setHourMinus()
        }

        imgv_book_hours_plus.setOnClickListener {
            setHourPlus()
        }

        btn_book_table.setOnClickListener {
            createBookTable()
        }
    }

    private fun createBookTable() {
        var dayBook = txv_book_date.text.toString()
        var timeBook = txv_book_time.text.toString()
        var bookTableItemModel: BookTableItemModel = BookTableItemModel(idTable, dayBook, timeBook, countHours)
        bookTableViewModel.createNewBookTable(bookTableItemModel)
        bookTableViewModel.createBookTable().observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun initListHour() {
        for (i in 6..23) {
            var hour = Hour("$i:00", true, 0)
            currentListHour.add(hour)
        }
//        hourAdapter.submitList(currentListHour)
//        Log.d("qweList", hourAdapter.currentList.toString())
    }

    private fun showDatePickerDialog() {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        DatePickerDialog(requireContext(), this, year, month, day).show()
    }

    private fun showTimePickerDialog() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(p0: DatePicker?, yearDialog: Int, monthDialog: Int, dayDialog: Int) {
        calendar.set(Calendar.YEAR, yearDialog)
        calendar.set(Calendar.MONTH, monthDialog)
        calendar.set(Calendar.DAY_OF_MONTH, dayDialog)
        txv_book_date.text = SimpleDateFormat("dd, MMM yyyy").format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onTimeSet(p0: TimePicker?, hourDialog: Int, minuteDialog: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourDialog)
        calendar.set(Calendar.MINUTE, minuteDialog)
        txv_book_time.text = SimpleDateFormat("HH:mm").format(calendar.time)
    }

    private fun setHourPlus() {
        if (countHours == 8) {
            Toast.makeText(
                activity,
                "Максимальное время бронирования - 8 часов",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            countHours++
            txv_book_number_hour.text = countHours.toString()
        }
    }

    private fun setHourMinus() {
        if (countHours == 1) {
            Toast.makeText(
                activity,
                "Минимальное время бронирования - 1 час",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            countHours--
            txv_book_number_hour.text = countHours.toString()
        }
    }

    override fun onClick(titleHour: String) {
        Log.d("qweLLL", "wqeqwe")
        txv_book_time.text = titleHour
    }

    private fun showReviewDialog() {
        val reviewDialogBuilder = AlertDialog.Builder(activity)

        var reviewView = layoutInflater.inflate(R.layout.fragment_list_hour, null)
//        reviewView.hour_list.adapter = hourAdapter
        reviewDialogBuilder.setView(reviewView)

//        var recycler: View =

//        val gridView = GridView(activity)
//
//        val mList: MutableList<Int?> = ArrayList()
//        for (i in 1..35) {
//            mList.add(i)
//        }
//
//        val adapter: ArrayAdapter<Hour> =
//            ArrayAdapter(requireContext(), R.layout.cardview_item_hour, currentListHour)
//        gridView.adapter = adapter
//        gridView.numColumns = 4
//        gridView.onItemClickListener =
//            OnItemClickListener { parent, view, position, id ->
//                // do something here
//            }

//        reviewDialogBuilder.setView(gridView)
        reviewDialogBuilder.setTitle("Ваш отзыв")
        reviewDialogBuilder.setCancelable(true)
        reviewDialog = reviewDialogBuilder.create()
        reviewDialogBuilder.show()
    }
}