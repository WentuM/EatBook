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
    fun provideUserInteractor(
        userRepository: UserRepository,
        context: CoroutineContext
    ): UserInteractor {
        return UserInteractor(userRepository, context)
    }

    @Provides
    fun provideRestaurantInteractor(
        restaurantRepository: RestaurantRepository,
        userRepository: UserRepository,
        favouritesRepository: FavouritesRepository,
        context: CoroutineContext
    ): RestaurantInteractor {
        return RestaurantInteractor(restaurantRepository, userRepository, favouritesRepository, context)
    }

    @Provides
    fun provideSaleInteractor(
        saleRepository: SaleRepository,
        context: CoroutineContext
    ): SaleInteractor {
        return SaleInteractor(saleRepository, context)
    }

    @Provides
    fun provideTableInteractor(
        tableRepository: TableRepository,
        context: CoroutineContext
    ): TableInteractor {
        return TableInteractor(tableRepository, context)
    }

    @Provides
    fun provideReviewInteractor(
        reviewRepository: ReviewRepository,
        userRepository: UserRepository,
        context: CoroutineContext
    ): ReviewInteractor {
        return ReviewInteractor(reviewRepository, userRepository, context)
    }

    @Provides
    fun provideBookTableInteractor(
        bookTableRepository: BookTableRepository,
        userRepository: UserRepository,
        tableRepository: TableRepository,
        context: CoroutineContext
    ): BookTableInteractor {
        return BookTableInteractor(bookTableRepository, tableRepository, userRepository, context)
    }

    @Provides
    fun provideFavouriteRestInteractor(
        favouritesRepository: FavouritesRepository,
        context: CoroutineContext
    ): FavouritesInteractor {
        return FavouritesInteractor(favouritesRepository, context)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO
}