package com.example.data.di.component

import com.example.data.di.module.ConverterModule
import com.example.data.di.module.DbModule
import com.example.data.di.module.FireBaseModule
import com.example.data.di.module.RepositoryModule
import com.example.data.repository.UserRepositoryImpl
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

//@Singleton
//@Subcomponent(modules = [FireBaseModule::class, RepositoryModule::class, ConverterModule::class, DbModule::class])
//interface DataComponent {
//}