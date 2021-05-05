package com.example.eatbook.ui.restaurants.detail.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.restaurants.detail.RestaurantDetailFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [RestaurantDetailModule::class])
interface RestaurantDetailComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): RestaurantDetailComponent
    }

    fun inject(restaurantDetailFragment: RestaurantDetailFragment)
}