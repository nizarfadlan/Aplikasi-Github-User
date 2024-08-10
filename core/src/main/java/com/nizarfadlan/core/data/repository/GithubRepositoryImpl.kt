package com.nizarfadlan.core.data.repository

import com.nizarfadlan.core.domain.common.ResultState
import com.nizarfadlan.core.domain.model.UserEntity
import com.nizarfadlan.core.data.remote.response.ItemsItem
import com.nizarfadlan.core.data.remote.response.SearchUserResponse
import com.nizarfadlan.core.domain.datasource.GithubDataSource
import com.nizarfadlan.core.domain.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GithubRepositoryImpl(
    private val dataSource: GithubDataSource
) : GithubRepository {
    override fun getAllUsers(username: String): Flow<ResultState<SearchUserResponse>> = dataSource.getAllUsers(username).flowOn(
        Dispatchers.IO)

    override fun getDetailUser(username: String): Flow<ResultState<UserEntity>> = dataSource.getDetailUser(username).flowOn(
        Dispatchers.IO)

    override fun getFollowers(username: String): Flow<ResultState<List<ItemsItem>>> = dataSource.getFollowers(username).flowOn(
        Dispatchers.IO)

    override fun getFollowing(username: String): Flow<ResultState<List<ItemsItem>>> = dataSource.getFollowing(username).flowOn(
        Dispatchers.IO)

    override fun getAllFavoriteUsers(): Flow<List<UserEntity>> = dataSource.getAllFavoriteUsers().flowOn(
        Dispatchers.IO)

    override suspend fun updateFavoriteUser(isFavorite: Boolean, username: String) = dataSource.updateFavoriteUser(isFavorite, username)
}