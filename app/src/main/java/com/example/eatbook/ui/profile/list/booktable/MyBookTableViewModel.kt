package com.example.eatbook.ui.profile.list.booktable

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactor.BookTableInteractor
import com.example.domain.model.BookTable
import com.example.domain.model.Restaurant
import com.example.domain.model.Review
import com.example.eatbook.ui.profile.list.booktable.model.MyBookTableItemModel
import com.example.eatbook.ui.reviews.list.model.ReviewList
import com.example.eatbook.ui.tables.booking.list.model.BookTableItemModel
import kotlinx.android.synthetic.main.fragment_list_my_book_table.view.*
import kotlinx.coroutines.launch
import java.lang.Exception

class MyBookTableViewModel(
    private val bookTableInteractor: BookTableInteractor
) : ViewModel() {

    private val _myTables = MutableLiveData<List<MyBookTableItemModel>>()
    private val _deleteMyTable: MutableLiveData<String> = MutableLiveData()


    fun myTables(): LiveData<List<MyBookTableItemModel>> = _myTables
    fun deleteMyTable(): LiveData<String> = _deleteMyTable

    fun getAllMyTables(view: View) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _myTables.value =
                    mapBookTableToMyBookTableItemList(bookTableInteractor.getAllMyTable())
            } catch (e: Exception) {

            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    fun deleteMyTableById(view: View, id: String) {
        viewModelScope.launch {
            try {
                view.progress_list.visibility = View.VISIBLE
                _deleteMyTable.value = bookTableInteractor.deleteMyTableById(id)
            } catch (e: Exception) {
                _deleteMyTable.value = "Что-то пошло не так"
            } finally {
                view.progress_list.visibility = View.GONE
            }
        }
    }

    private fun mapBookTableToMyBookTableItemList(listBookTable: List<BookTable>): List<MyBookTableItemModel> {
        var resultMyBookTableItemList: ArrayList<MyBookTableItemModel> = ArrayList()
        for (bookTable: BookTable in listBookTable) {
            with(bookTable) {
                var myBookTableItemModel = MyBookTableItemModel(
                    id,
                    idTable,
                    day,
                    time,
                    countHour,
                    imageTable,
                    nameTable,
                    idRestaurant,
                    nameRestaurant
                )
                resultMyBookTableItemList.add(myBookTableItemModel)
            }
        }
        return resultMyBookTableItemList
    }
}