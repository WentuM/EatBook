package com.example.eatbook.ui.tables.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.tables.list.TableFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [TableModule::class])
interface TableComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): TableComponent
    }

    fun inject(tableFragment: TableFragment)
}