package com.example.eatbook.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.domain.model.Restaurant
import com.example.eatbook.EatBookApp
import com.example.eatbook.R
import com.example.eatbook.ui.restaurants.list.RestaurantAdapter
import com.example.eatbook.ui.restaurants.list.RestaurantViewModel
import kotlinx.android.synthetic.main.cardview_item_restaurant.*
import kotlinx.android.synthetic.main.cardview_item_restaurant.view.*
import kotlinx.android.synthetic.main.fragment_list_rest.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class FavouritesFragment: Fragment(), FavouritesAdapter.FavouritesItemHandler {

    @Inject
    lateinit var favouritesViewModel: FavouritesViewModel
//    private var currentListRest = arrayListOf<Restaurant>()
    private val favouritesAdapter =
        FavouritesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.favouritesComponentFactory()
            .create(this).inject(this)
        val root = inflater.inflate(R.layout.fragment_list_rest, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rest_list.adapter = favouritesAdapter

        favouritesViewModel.restaurants().observe(viewLifecycleOwner, Observer {
            favouritesAdapter.submitList(it)
//            currentListRest.addAll(it)
        })

        favouritesViewModel.getAllRestaurants()
    }

    override fun onItemClick(idRestaurant: String) {
        val bundle = Bundle()
        bundle.putString("idRestaurant", idRestaurant)
        findNavController().navigate(R.id.action_navigation_favourites_to_navigation_rest_detail, bundle)
    }

    override fun onFavourite(idRestaurant: String, likeRest: Int) {
        if (likeRest == 1) {
            btn_rest_favourite.setBackgroundResource(R.drawable.ic_baseline_favorite_24_red)
        } else {
            btn_rest_favourite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }
        favouritesViewModel.setLikeForRestaurant(idRestaurant)
        favouritesViewModel.likeRest().observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
    }
}