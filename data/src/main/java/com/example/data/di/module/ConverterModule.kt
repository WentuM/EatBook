package com.example.data.di.module

import com.example.data.mappers.*
import dagger.Module
import dagger.Provides

@Module
class ConverterModule {

    @Provides
    fun provideRestaurantConverter(): RestaurantConverter = RestaurantConverterImpl()

    @Provides
    fun provideBookTableConverter(): BookTableConverter = BookTableConverterImpl()

    @Provides
    fun provideReviewConverter(): ReviewConverter = ReviewConverterImpl()

    @Provides
    fun provideSaleConverter(): SaleConverter = SaleConverterImpl()

    @Provides
    fun provideTableConverter(): TableConverter = TableConverterImpl()

    @Provides
    fun provideUserConverter(): UserConverter = UserConverterImpl()
}