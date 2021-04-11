package com.example.data.di.module

import com.example.data.mappers.RestaurantConverter
import com.example.data.mappers.RestaurantConverterImpl
import dagger.Module
import dagger.Provides

@Module
class ConverterModule {

    @Provides
    fun provideRestaurantConverter(): RestaurantConverter = RestaurantConverterImpl()
}