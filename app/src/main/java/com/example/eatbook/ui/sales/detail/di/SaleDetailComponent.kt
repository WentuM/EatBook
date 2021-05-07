package com.example.eatbook.ui.sales.detail.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.sales.detail.SaleDetailFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SaleDetailModule::class])
interface SaleDetailComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): SaleDetailComponent
    }

    fun inject(saleDetailFragment: SaleDetailFragment)
}