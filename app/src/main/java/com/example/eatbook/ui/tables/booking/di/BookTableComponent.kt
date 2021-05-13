package com.example.eatbook.ui.tables.booking.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.tables.booking.BookTableFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [BookTableModule::class])
interface BookTableComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): BookTableComponent
    }

    fun inject(bookTableFragment: BookTableFragment)
}