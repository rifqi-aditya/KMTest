package com.rifqi.kmtest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rifqi.kmtest.data.source.remote.paging.UserPagingSource
import com.rifqi.kmtest.data.source.remote.response.DataItem
import com.rifqi.kmtest.data.source.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val apiService: ApiService) {

    fun getUsers(): Flow<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(apiService) }
        ).flow
    }

    companion object {
        @Volatile
        private var instance: UsersRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UsersRepository = instance ?: synchronized(this) {
            instance ?: UsersRepository(apiService)
        }.also { instance = it }
    }
}