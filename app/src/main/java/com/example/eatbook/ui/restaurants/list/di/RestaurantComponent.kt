package com.example.eatbook.ui.restaurants.list.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.restaurants.list.RestaurantFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [RestaurantModule::class])
interface RestaurantComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): RestaurantComponent
    }

    fun inject(restaurantFragment: RestaurantFragment)
}