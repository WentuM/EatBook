package com.example.eatbook.ui.sales.list.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactor.SaleInteractor
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.sales.list.SaleViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class SaleModule {

    @Provides
    @IntoMap
    @ViewModelKey(SaleViewModel::class)
    fun provideViewModel(interactor: SaleInteractor): ViewModel {
        return SaleViewModel(
            interactor
        )
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): SaleViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(SaleViewModel::class.java)
    }
}