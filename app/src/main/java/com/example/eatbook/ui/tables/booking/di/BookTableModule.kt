package com.example.eatbook.ui.tables.booking.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.interactor.TableInteractor
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.tables.booking.BookTableViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class BookTableModule {

    @Provides
    @IntoMap
    @ViewModelKey(BookTableViewModel::class)
    fun provideViewModel(interactor: TableInteractor): ViewModel {
        return BookTableViewModel(
            interactor
        )
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): BookTableViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(BookTableViewModel::class.java)
    }
}