package com.example.eatbook.ui.profile.list.booktable.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.profile.list.booktable.MyBookTableFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [MyBookTableModule::class])
interface MyBookTableComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): MyBookTableComponent
    }

    fun inject(myBookTableFragment: MyBookTableFragment)
}