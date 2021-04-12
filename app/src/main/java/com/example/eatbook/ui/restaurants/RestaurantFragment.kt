package com.example.eatbook.ui.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.RestaurantInteractor
import com.example.domain.UserInteractor
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.ViewModelFactory
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers

class RestaurantFragment : Fragment(), RestaurantAdapter.RestItemHandler {

    private lateinit var restaurantViewModel: RestaurantViewModel
    private lateinit var application: EatBookApp

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        application = activity?.application as (EatBookApp)
        restaurantViewModel = ViewModelProvider(this, initFactory()).get(RestaurantViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_view.visibility = View.VISIBLE
        
        createSearchView()
    }


    private fun initFactory(): ViewModelFactory = ViewModelFactory(
        userInteractor = UserInteractor(application.repositoryUser, Dispatchers.IO),
        restaurantInteractor = RestaurantInteractor(application.repositoryRestaurant, Dispatchers.IO)
    )


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
        TODO("Not yet implemented")
    }

    override fun onFavourite(idRestaurant: String) {
        TODO("Not yet implemented")
    }
}