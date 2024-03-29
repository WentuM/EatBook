package com.example.eatbook.ui.sales.detail.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactor.SaleInteractor
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.sales.detail.SaleDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class SaleDetailModule {

    @Provides
    @IntoMap
    @ViewModelKey(SaleDetailViewModel::class)
    fun provideViewModel(interactor: SaleInteractor): ViewModel {
        return SaleDetailViewModel(
            interactor
        )
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): SaleDetailViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(SaleDetailViewModel::class.java)
    }
}