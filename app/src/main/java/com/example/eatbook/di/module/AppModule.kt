package com.example.eatbook.di.module

import android.app.Application
import android.content.Context
import com.example.domain.interactor.*
import com.example.domain.interfaces.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
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
    fun provideTableInteractor(tableRepository: TableRepository, context: CoroutineContext): TableInteractor {
        return TableInteractor(tableRepository, context)
    }

    @Provides
    @Singleton
    fun provideReviewInteractor(reviewRepository: ReviewRepository, userRepository: UserRepository,context: CoroutineContext): ReviewInteractor {
        return ReviewInteractor(reviewRepository, userRepository, context)
    }

    @Provides
    fun provideBookTableInteractor(bookTableRepository: BookTableRepository, context: CoroutineContext): BookTableInteractor {
        return BookTableInteractor(bookTableRepository, context)
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