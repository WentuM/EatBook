package com.example.eatbook.di.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelProviderFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val found = creators.entries.find {
            modelClass.isAssignableFrom(it.key)
        }
        val creator = found?.value
            ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHEKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}