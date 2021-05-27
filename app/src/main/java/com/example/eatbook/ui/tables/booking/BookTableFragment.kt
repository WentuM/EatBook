package com.example.eatbook.ui.tables.booking

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.tables.booking.list.HourAdapter
import com.example.eatbook.ui.tables.booking.list.model.BookTableItemModel
import com.example.eatbook.ui.tables.booking.list.model.Hour
import com.example.eatbook.ui.tables.booking.list.model.TableItemModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_book_table_create.view.*
import kotlinx.android.synthetic.main.fragment_book_rest.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class BookTableFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    HourAdapter.HourItemHandler {

    @Inject
    lateinit var bookTableViewModel: BookTableViewModel
    private val hourAdapter: HourAdapter = HourAdapter(this)
    private var currentListHour = arrayListOf<Hour>()
    private var idTable: String = ""
    private lateinit var tableItemModel: TableItemModel
    private var countHours: Int = 1
    private var showTimeList: Boolean = true


    companion object {
        private lateinit var calendar: Calendar
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
        bookTableViewModel.getTableById(idTable, view)
        hour_list.adapter = hourAdapter
        initFields()
        initClicks(view)
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
    }

    private fun initClicks(view: View) {
        imgv_book_date.setOnClickListener {
            showDatePickerDialog()
        }

        imgv_book_time.setOnClickListener {
            if (showTimeList) {
                bookTableViewModel.getBookTableByDay(txv_book_date.text.toString(), idTable, view)
                bookTableViewModel.bookTable().observe(viewLifecycleOwner, Observer {
                    if (it.isNotEmpty()) {
                        for (i in 0 until currentListHour.size) {
                            //всем занятым часам в день бронирования проставляю
                            //false, чтобы нельзя было забронировать
                            if (it.contains(currentListHour[i])) {
                                val temp = it.indexOf(currentListHour[i])
                                for (j in i until i + it[temp].countHour) {
                                    currentListHour[j].exist = false
                                }
                            }
                        }
                    }
                    hourAdapter.submitList(currentListHour)
                    hour_list.visibility = View.VISIBLE
                    showTimeList = false
                })
            } else {
                hour_list.visibility = View.GONE
                showTimeList = true
            }
        }

        imgv_book_hours_minus.setOnClickListener {
            setHourMinus()
        }

        imgv_book_hours_plus.setOnClickListener {
            setHourPlus()
        }

        btn_book_table.setOnClickListener {
            if (!bookTableViewModel.getCurrentUser()) {
                findNavController().navigate(R.id.action_navigation_rest_detail_to_navigation_sign_in)
            } else {
                createBookTable(view)
            }
        }
    }

    private fun createBookTable(view: View) {
        val dayBook = txv_book_date.text.toString()
        val timeBook = txv_book_time.text.toString()
        //если проходит всю проверку, то даю возможность забронировать
        if (checkFieldsBook(timeBook)) {
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
            bookTableViewModel.createNewBookTable(bookTableItemModel, view)
            bookTableViewModel.createBookTable().observe(viewLifecycleOwner, Observer {
                if (it == "Вы успешно забронировали столик") {
                    showReviewDialog(it, bookTableItemModel)
                } else {
                    Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun initListHour() {
        for (i in 6..23) {
            val hour = Hour("$i:00", true, 0)
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

    private fun checkFieldsBook(timeBook: String): Boolean {
        //если человек не выбрал время бронирования
        if (timeBook.isEmpty()) {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                "Выберите время бронирования",
                Snackbar.LENGTH_LONG
            ).show()
            return false
        }
        //проверка, если человек выбрал свободное время, и его количество часов
        //бронирования не пересекается с бронированием другого человека
        val splitTimeBook = timeBook.split(":")
        val j = splitTimeBook[0].toInt() - 6
        if (j + countHours <= currentListHour.size) {
            for (i in j until j + countHours) {
                if (!currentListHour[i].exist) {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        "Время на ${currentListHour[i]} занято",
                        Snackbar.LENGTH_LONG
                    ).show()
                    return false
                }
            }
        } else {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                "Невозможное время бронирования",
                Snackbar.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    override fun onClick(titleHour: String) {
        txv_book_time.text = titleHour
    }

    @SuppressLint("SetTextI18n")
    private fun showReviewDialog(str: String, bookTableItemModel: BookTableItemModel) {
        val reviewDialog = AlertDialog.Builder(activity)
        val reviewView = layoutInflater.inflate(R.layout.dialog_book_table_create, null)
        with(reviewView) {
            txv_book_table_result_title.text =
                "Вы успешно забронировали ${bookTableItemModel.nameTable} в ресторане '${bookTableItemModel.nameRestaurant}'."
            txv_book_table_result_date.text = "Ваша дата бронирования: ${bookTableItemModel.day}."
            txv_book_table_result_time.text =
                "Ваше время бронирования: ${bookTableItemModel.time} на ${bookTableItemModel.countHour} часа."
        }

        reviewDialog.setView(reviewView)
        reviewDialog.setCancelable(false)
            .setPositiveButton(
                "Перейти в 'Мои бронироваия'",
                object : DialogInterface.OnClickListener {
                    @SuppressLint("SimpleDateFormat")
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        findNavController().navigate(R.id.action_navigation_rest_book_to_navigation_my_table_book)
                        p0?.dismiss()
                    }

                })
        reviewDialog.create()
        reviewDialog.show()
    }
}