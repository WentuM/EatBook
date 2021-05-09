package com.example.eatbook.ui.restaurants.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import kotlinx.android.synthetic.main.fragment_list_rest.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class RestaurantFragment : Fragment(), RestaurantAdapter.RestItemHandler {

    @Inject
    lateinit var restaurantViewModel: RestaurantViewModel
    private lateinit var application: EatBookApp
    private val restaurantAdapter =
        RestaurantAdapter(this)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.restaurantsListComponentFactory()
            .create(this).inject(this)
        val root = inflater.inflate(R.layout.fragment_list_rest, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_view.visibility = View.VISIBLE

        rest_list.adapter = restaurantAdapter

        restaurantViewModel.restaurants().observe(viewLifecycleOwner, Observer {
            restaurantAdapter.submitList(it)
        })

        restaurantViewModel.getAllRestaurants()
        createSearchView()
    }


    private fun createSearchView() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(cityName: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                //вызовется при изменении ведённого текста
                return true
            }
        })
    }

    override fun onClick(idRestaurant: String) {
        Log.d("qwe12312321", "qwef")
        var bundle = Bundle()
        bundle.putString("idRestaurant", idRestaurant)
        findNavController().navigate(R.id.action_navigation_home_to_navigation_rest_detail, bundle)
    }

    override fun onFavourite(idRestaurant: String) {
        Log.d("qwe121234321", "qwef")
        TODO("Not yet implemented")
    }
}