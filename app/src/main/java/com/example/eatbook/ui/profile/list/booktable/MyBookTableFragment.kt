package com.example.eatbook.ui.profile.list.booktable

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import kotlinx.android.synthetic.main.cardview_item_restaurant.*
import kotlinx.android.synthetic.main.fragment_list_my_book_table.*
import kotlinx.android.synthetic.main.fragment_list_rest.*
import kotlinx.android.synthetic.main.toolbar.*
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

    override fun onItemClick(idBookTable: String) {
        val bundle = Bundle()
        bundle.putString("idRestaurant", idBookTable)
        findNavController().navigate(R.id.action_navigation_home_to_navigation_rest_detail, bundle)
    }

    override fun onFavourite(idBookTable: String) {
//        restaurantViewModel.setLikeForRestaurant(idBookTable)
//        restaurantViewModel.likeRest().observe(viewLifecycleOwner, Observer {
//            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
//        })
    }
}