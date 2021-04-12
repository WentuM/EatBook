package com.example.eatbook.di.component

import android.content.Context
import com.example.eatbook.di.module.AppModule
import com.example.data.repository.UserRepositoryImpl
import com.example.eatbook.ui.dashboard.di.ProfileComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(applicatonContext: Context)

        fun build(): AppComponent
    }

    fun provideApp(): Context

    fun inject(userRepositoryImpl: UserRepositoryImpl)

    fun profileComponentFactory(): ProfileComponent.Factory

//    fun userDetailsComponentFactory(): UserDetailsComponent.Factory

}