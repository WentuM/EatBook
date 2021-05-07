package com.example.eatbook.ui.sales.list.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.sales.list.SaleFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SaleModule::class])
interface SaleComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): SaleComponent
    }

    fun inject(saleFragment: SaleFragment)
}