package com.rifqi.kmtest.di

import android.content.Context
import com.rifqi.kmtest.data.repository.UsersRepository
import com.rifqi.kmtest.data.source.remote.retrofit.ApiConfig

object Injection {
    fun provideUsersRepository(context: Context): UsersRepository {
        val apiService = ApiConfig.getApiService()
        return UsersRepository.getInstance(apiService)
    }
}