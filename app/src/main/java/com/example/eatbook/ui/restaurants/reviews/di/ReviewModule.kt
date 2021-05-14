package com.example.eatbook.ui.restaurants.reviews.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactor.ReviewInteractor
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.restaurants.reviews.ReviewViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class ReviewModule {

    @Provides
    @IntoMap
    @ViewModelKey(ReviewViewModel::class)
    fun provideViewModel(interactor: ReviewInteractor): ViewModel {
        return ReviewViewModel(interactor)
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): ReviewViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(ReviewViewModel::class.java)
    }
}