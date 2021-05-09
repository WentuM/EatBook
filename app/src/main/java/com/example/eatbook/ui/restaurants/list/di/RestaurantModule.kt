package com.example.eatbook.ui.restaurants.list.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactor.RestaurantInteractor
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.restaurants.list.RestaurantViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class RestaurantModule {

    @Provides
    @IntoMap
    @ViewModelKey(RestaurantViewModel::class)
    fun provideViewModel(interactor: RestaurantInteractor): ViewModel {
        return RestaurantViewModel(
            interactor
        )
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): RestaurantViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(RestaurantViewModel::class.java)
    }
}