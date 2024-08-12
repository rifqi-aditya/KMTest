package com.rifqi.kmtest.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rifqi.kmtest.data.repository.UsersRepository
import com.rifqi.kmtest.di.Injection
import com.rifqi.kmtest.ui.third.ThirdViewModel

class ViewModelFactory(
    private val usersRepository: UsersRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ThirdViewModel::class.java) -> {
                ThirdViewModel(usersRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideUsersRepository(context),
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}