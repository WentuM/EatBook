package com.example.eatbook.ui.favourites

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
import com.example.eatbook.ui.favourites.model.FavouritesListModel
import com.example.eatbook.ui.restaurants.list.model.RestaurantListModel
import kotlinx.android.synthetic.main.cardview_item_restaurant.*
import kotlinx.android.synthetic.main.fragment_list_rest.*
import javax.inject.Inject

class FavouritesFragment : Fragment(), FavouritesAdapter.FavouritesItemHandler {

    @Inject
    lateinit var favouritesViewModel: FavouritesViewModel
    private val favouritesAdapter =
        FavouritesAdapter(this)
    private var currentListRest = arrayListOf<FavouritesListModel>()

    companion object {
        private const val ID_RESTAURANT = "idRestaurant"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EatBookApp.appComponent.favouritesComponentFactory()
            .create(this).inject(this)
        return inflater.inflate(R.layout.fragment_list_rest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rest_list.adapter = favouritesAdapter

        favouritesViewModel.restaurants().observe(viewLifecycleOwner, Observer {
            favouritesAdapter.submitList(it)
            currentListRest.addAll(it)
            if (it.isEmpty()) {
                txv_rest_dont_favourite.visibility = View.VISIBLE
            }
        })

        favouritesViewModel.getAllRestaurants(view)
    }

    override fun onItemClick(idRestaurant: String) {
        val bundle = Bundle()
        bundle.putString(ID_RESTAURANT, idRestaurant)
        findNavController().navigate(
            R.id.action_navigation_favourites_to_navigation_rest_detail,
            bundle
        )
    }

    override fun onFavourite(restaurant: FavouritesListModel) {
        val indexRestaurant = currentListRest.indexOf(restaurant)
        favouritesViewModel.setLikeForRestaurant(restaurant.id)
        favouritesViewModel.likeRest().observe(viewLifecycleOwner, Observer {
            val resultLike = it
            if (resultLike == "Ресторан добавлен в избранное") {
                currentListRest[indexRestaurant].likeRest = 1
            } else if (resultLike == "Ресторан удалён из избранного") {
                currentListRest[indexRestaurant].likeRest = 0
            }
            favouritesAdapter.notifyItemChanged(indexRestaurant)
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
    }
}