package com.example.eatbook.ui.favourites.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.favourites.FavouritesFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [FavouritesModule::class])
interface FavouritesComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): FavouritesComponent
    }

    fun inject(favouritesFragment: FavouritesFragment)
}