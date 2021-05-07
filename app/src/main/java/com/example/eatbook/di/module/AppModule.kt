package com.example.eatbook.di.module

import android.app.Application
import android.content.Context
import com.example.domain.RestaurantInteractor
import com.example.domain.SaleInteractor
import com.example.domain.UserInteractor
import com.example.domain.interfaces.RestaurantRepository
import com.example.domain.interfaces.SaleRepository
import com.example.domain.interfaces.UserRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class AppModule() {

    @Provides
    @Singleton
    fun provideUserInteractor(userRepository: UserRepository, context: CoroutineContext): UserInteractor {
        return UserInteractor(userRepository, context)
    }

    @Provides
    @Singleton
    fun provideRestaurantInteractor(restaurantRepository: RestaurantRepository, context: CoroutineContext): RestaurantInteractor {
        return RestaurantInteractor(restaurantRepository, context)
    }

    @Provides
    @Singleton
    fun provideSaleInteractor(saleRepository: SaleRepository, context: CoroutineContext): SaleInteractor {
        return SaleInteractor(saleRepository, context)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

//    @Provides
//    @Singleton
//    fun provideContext(): Context = app.applicationContext
}