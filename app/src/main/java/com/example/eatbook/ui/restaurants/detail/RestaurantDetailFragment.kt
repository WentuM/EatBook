package com.example.eatbook.ui.restaurants.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.domain.model.Restaurant
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_restaurant_detail.*
import javax.inject.Inject

class RestaurantDetailFragment : Fragment() {

    @Inject
    lateinit var restaurantDetailViewModel: RestaurantDetailViewModel
    private var idRestaurant: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("idRestaurant")?.let {
            idRestaurant = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.restaurantDetailComponentFactory()
            .create(this)
            .inject(this)
        val root = inflater.inflate(R.layout.fragment_restaurant_detail, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var restaurant: Restaurant = Restaurant("", "", "", 0.0, "", 0, "")
        restaurantDetailViewModel.getRestaurant().observe(viewLifecycleOwner, Observer {
            restaurant = it
        })
        restaurantDetailViewModel.getRestaurantById(idRestaurant)

        initFields(restaurant)
    }

    private fun initFields(restaurant: Restaurant) {
        txv_rest_title.text = restaurant.title
        txv_rest_descr.text = restaurant.description
        txv_rest_raiting.text = restaurant.raiting.toString()
        txv_rest_price.text = restaurant.price.toString()
        ratingbar_rest.rating = restaurant.raiting.toFloat()
//        Glide.with(this).load(restaurant.imageRest).into(imgv_rest)
    }

}