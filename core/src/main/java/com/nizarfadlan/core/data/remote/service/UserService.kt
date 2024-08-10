package com.nizarfadlan.core.data.remote.service

import com.nizarfadlan.core.data.remote.response.ItemsItem
import com.nizarfadlan.core.data.remote.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("/search/users")
    suspend fun searchUser(
        @Query("q", encoded = true) query: String
    ): SearchUserResponse

    @GET("/users/{username}")
    suspend fun detailUser(
        @Path("username") id: String
    ): com.nizarfadlan.core.data.remote.response.DetailUserResponse

    @GET("/users/{username}/followers")
    suspend fun followersUser(
        @Path("username") id: String
    ): List<ItemsItem>

    @GET("/users/{username}/following")
    suspend fun followingUser(
        @Path("username") id: String,
    ): List<ItemsItem>
}