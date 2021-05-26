package com.example.eatbook.ui.restaurants.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import kotlinx.android.synthetic.main.fragment_profile.*
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
        restaurantDetailViewModel.getRestaurantById(idRestaurant, view)
        initFields()
        initClick()
    }

    @SuppressLint("SetTextI18n")
    private fun initFields() {
        with(restaurantDetailViewModel) {
            getRestaurant().observe(viewLifecycleOwner, Observer {
                txv_rest_title.text = it.title
                txv_rest_descr.text = it.description
                txv_rest_raiting.text = it.rating.toString()
                txv_rest_price.text = it.price.toString() + " ₽"
                txv_rest_address.text = it.address
                ratingbar_rest.rating = it.rating.toFloat()
                Glide.with(this@RestaurantDetailFragment).load(it.imageRest).into(imgv_rest)
            })
        }

    }

    private fun initClick() {
        btn_rest_book.setOnClickListener {
            if (!restaurantDetailViewModel.getCurrentUser()) {
                findNavController().navigate(R.id.action_navigation_rest_detail_to_navigation_sign_in)
            } else {
                var bundle = Bundle()
                bundle.putString("idRestaurant", idRestaurant)
                findNavController().navigate(R.id.action_navigation_rest_detail_to_navigation_rest_tables, bundle)
            }
        }
        btn_rest_reviews.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("idRestaurant", idRestaurant)
            findNavController().navigate(R.id.action_navigation_rest_detail_to_navigation_review, bundle)
        }
    }

}