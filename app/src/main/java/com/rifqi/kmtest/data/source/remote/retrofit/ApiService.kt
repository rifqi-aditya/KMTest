package com.rifqi.kmtest.data.source.remote.retrofit

import com.rifqi.kmtest.data.source.remote.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UsersResponse
}