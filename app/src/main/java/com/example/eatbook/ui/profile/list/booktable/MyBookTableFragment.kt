package com.example.eatbook.ui.profile.list.booktable

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import kotlinx.android.synthetic.main.fragment_list_my_book_table.*
import javax.inject.Inject

class MyBookTableFragment : Fragment(), MyBookTableAdapter.MyBookTableItemHandler {

    @Inject
    lateinit var myBookTableViewModel: MyBookTableViewModel
    private val myBookTableAdapter =
        MyBookTableAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.myBookTableComponentFactory()
            .create(this).inject(this)
        val root = inflater.inflate(R.layout.fragment_list_my_book_table, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        my_book_table_list.adapter = myBookTableAdapter


        myBookTableViewModel.myTables().observe(viewLifecycleOwner, Observer {
            myBookTableAdapter.submitList(it)
            if (it.isEmpty()) {
                txv_my_dont_book.visibility = View.VISIBLE
            }
        })

        myBookTableViewModel.getAllMyTables(view)
    }

    override fun onRedirectToRest(idRestaurant: String) {
        val bundle = Bundle()
        bundle.putString("idRestaurant", idRestaurant)
        findNavController().navigate(
            R.id.action_navigation_my_table_book_to_navigation_rest_detail,
            bundle
        )
    }

    override fun onDeleteMyTable(idBookTable: String) {
        view?.let { showReviewDialog(it, idBookTable) }
    }

    private fun showReviewDialog(view: View, idBookTable: String) {
        val reviewDialog = AlertDialog.Builder(activity)

        reviewDialog.setTitle("Вы действительно хотите удалить бронирование?")
        reviewDialog.setCancelable(false)
            .setPositiveButton("Удалить") { dialogInterface, p1 ->
                myBookTableViewModel.deleteMyTableById(view, idBookTable)
                myBookTableViewModel.deleteMyTable().observe(viewLifecycleOwner, Observer {
                    Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                })
                findNavController().navigate(R.id.action_navigation_my_table_book_to_navigation_profile)
                dialogInterface?.dismiss()
            }
            .setNegativeButton(
                "Отмена"
            ) { dialogInterface, p1 -> dialogInterface?.cancel() }
        reviewDialog.create()
        reviewDialog.show()
    }
}