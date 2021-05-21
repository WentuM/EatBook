package com.example.eatbook.ui.favourites.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactor.FavouritesInteractor
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.favourites.FavouritesViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class FavouritesModule {

    @Provides
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    fun provideViewModel(interactor: FavouritesInteractor): ViewModel {
        return FavouritesViewModel(interactor)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): FavouritesViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(FavouritesViewModel::class.java)
    }
}