package com.example.eatbook.ui.tables.booking

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.tables.booking.list.HourAdapter
import com.example.eatbook.ui.tables.booking.list.model.BookTableItemModel
import com.example.eatbook.ui.tables.booking.list.model.Hour
import com.example.eatbook.ui.tables.booking.list.model.TableItemModel
import kotlinx.android.synthetic.main.fragment_book_rest.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class BookTableFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    HourAdapter.HourItemHandler {

    @Inject
    lateinit var bookTableViewModel: BookTableViewModel
    private val hourAdapter = HourAdapter(this)
    private var currentListHour = arrayListOf<Hour>()
    private var idTable: String = ""
    private lateinit var tableItemModel: TableItemModel
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
        bookTableViewModel.getTableById(idTable)
        val root = inflater.inflate(R.layout.fragment_book_rest, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hour_list.adapter = hourAdapter
        initFields()
        initClicks()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun initFields() {
        bookTableViewModel.getTable().observe(viewLifecycleOwner, Observer {
            tableItemModel = it
            book_rest_title.text = it.nameRestaurant
            txv_table_title.text = it.title
            txv_table_count_people.text = "Количество человек: " + it.countPlaces
        })
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
                for (hour: Hour in currentListHour) {
                    if (it.contains(hour)) {
                        hour.exist = false
                    }
                }
                hourAdapter.submitList(currentListHour)
                hour_list.visibility = View.VISIBLE
            })

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
        val dayBook = txv_book_date.text.toString()
        val timeBook = txv_book_time.text.toString()
        val bookTableItemModel: BookTableItemModel =
            BookTableItemModel(
                "",
                idTable,
                dayBook,
                timeBook,
                countHours,
                tableItemModel.image,
                tableItemModel.title,
                tableItemModel.idRestaurant,
                tableItemModel.nameRestaurant
            )
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
    }

    private fun showDatePickerDialog() {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        DatePickerDialog(requireContext(), this, year, month, day).show()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(p0: DatePicker?, yearDialog: Int, monthDialog: Int, dayDialog: Int) {
        calendar.set(Calendar.YEAR, yearDialog)
        calendar.set(Calendar.MONTH, monthDialog)
        calendar.set(Calendar.DAY_OF_MONTH, dayDialog)
        txv_book_date.text = SimpleDateFormat("dd, MMM yyyy").format(calendar.time)
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