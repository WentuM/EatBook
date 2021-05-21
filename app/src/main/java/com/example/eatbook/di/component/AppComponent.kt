package com.example.eatbook.di.component

import android.app.Application
import com.example.data.di.module.DbModule
import com.example.data.di.module.FireBaseModule
import com.example.data.di.module.RepositoryModule
import com.example.eatbook.di.module.AppModule
import com.example.eatbook.ui.favourites.di.FavouritesComponent
import com.example.eatbook.ui.profile.di.ProfileComponent
import com.example.eatbook.ui.restaurants.detail.di.RestaurantDetailComponent
import com.example.eatbook.ui.restaurants.list.di.RestaurantComponent
import com.example.eatbook.ui.reviews.di.ReviewComponent
import com.example.eatbook.ui.sales.detail.di.SaleDetailComponent
import com.example.eatbook.ui.sales.list.di.SaleComponent
import com.example.eatbook.ui.sign.`in`.di.SignInComponent
import com.example.eatbook.ui.sign.`in`.di.VerifyComponent
import com.example.eatbook.ui.tables.booking.di.BookTableComponent
import com.example.eatbook.ui.tables.di.TableComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DbModule::class, RepositoryModule::class, FireBaseModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun profileComponentFactory(): ProfileComponent.Factory

    fun verifyComponentFactory():  VerifyComponent.Factory

    fun signInComponentFactory(): SignInComponent.Factory

    fun restaurantsListComponentFactory(): RestaurantComponent.Factory

    fun restaurantDetailComponentFactory(): RestaurantDetailComponent.Factory

    fun salesListComponentFactory(): SaleComponent.Factory

    fun tableListComponentFactory(): TableComponent.Factory

    fun tableBookComponentFactory(): BookTableComponent.Factory

    fun saleDetailComponentFactory(): SaleDetailComponent.Factory

    fun reviewListComponentFactory(): ReviewComponent.Factory

    fun favouritesComponentFactory(): FavouritesComponent.Factory

}