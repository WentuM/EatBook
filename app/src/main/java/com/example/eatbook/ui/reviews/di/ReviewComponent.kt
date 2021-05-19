package com.example.eatbook.ui.reviews.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.reviews.list.ReviewFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ReviewModule::class])
interface ReviewComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance fragment: Fragment): ReviewComponent
    }

    fun inject(reviewFragment: ReviewFragment)
}