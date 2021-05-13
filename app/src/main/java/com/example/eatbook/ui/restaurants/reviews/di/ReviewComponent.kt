package com.example.eatbook.ui.restaurants.reviews.di

import androidx.fragment.app.Fragment
import com.example.eatbook.ui.restaurants.reviews.ReviewFragment
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