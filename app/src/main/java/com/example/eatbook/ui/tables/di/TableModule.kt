package com.example.eatbook.ui.tables.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eatbook.di.component.ViewModelKey
import com.example.eatbook.di.module.ViewModelModule
import com.example.eatbook.ui.tables.TableViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
class TableModule {

    @Provides
    @IntoMap
    @ViewModelKey(TableViewModel::class)
    fun provideViewModel(interactor: TableInteractor): ViewModel {
        return TableViewModel(
            interactor
        )
    }

    @Provides
    fun provideViewModelCreator(fragment: Fragment, viewModelFactory: ViewModelProvider.Factory): TableViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(TableViewModel::class.java)
    }
}