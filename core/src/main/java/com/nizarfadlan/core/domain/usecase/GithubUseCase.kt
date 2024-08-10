package com.nizarfadlan.core.domain.usecase

import com.nizarfadlan.core.data.remote.response.ItemsItem
import com.nizarfadlan.core.data.remote.response.SearchUserResponse
import com.nizarfadlan.core.domain.common.ResultState
import com.nizarfadlan.core.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface GithubUseCase {
    fun getAllUsers(username: String): Flow<ResultState<SearchUserResponse>>
    fun getDetailUser(username: String): Flow<ResultState<UserEntity>>
    fun getFollowers(username: String): Flow<ResultState<List<ItemsItem>>>
    fun getFollowing(username: String): Flow<ResultState<List<ItemsItem>>>
    fun getAllFavoriteUsers(): Flow<List<UserEntity>>
    suspend fun updateFavoriteUser(isFavorite: Boolean, username: String)
}