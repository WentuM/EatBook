package com.example.eatbook.ui.restaurants.detail.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactor.RestaurantInteractor
import com.example.domain.interactor.UserInteractor
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.restaurants.detail.RestaurantDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class RestaurantDetailModule {

    @Provides
    @IntoMap
    @ViewModelKey(RestaurantDetailViewModel::class)
    fun provideViewModel(interactor: RestaurantInteractor): ViewModel {
        return RestaurantDetailViewModel(
            interactor
        )
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): RestaurantDetailViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(RestaurantDetailViewModel::class.java)
    }
}