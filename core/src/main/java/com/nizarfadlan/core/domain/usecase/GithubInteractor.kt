package com.nizarfadlan.core.domain.usecase

import com.nizarfadlan.core.data.remote.response.ItemsItem
import com.nizarfadlan.core.data.remote.response.SearchUserResponse
import com.nizarfadlan.core.domain.common.ResultState
import com.nizarfadlan.core.domain.model.UserEntity
import com.nizarfadlan.core.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow

class GithubInteractor(private val githubRepository: GithubRepository): GithubUseCase {
    override fun getAllUsers(username: String): Flow<ResultState<SearchUserResponse>> = githubRepository.getAllUsers(username)
    override fun getDetailUser(username: String): Flow<ResultState<UserEntity>> = githubRepository.getDetailUser(username)
    override fun getFollowers(username: String): Flow<ResultState<List<ItemsItem>>> = githubRepository.getFollowers(username)
    override fun getFollowing(username: String): Flow<ResultState<List<ItemsItem>>> = githubRepository.getFollowing(username)
    override fun getAllFavoriteUsers(): Flow<List<UserEntity>> = githubRepository.getAllFavoriteUsers()
    override suspend fun updateFavoriteUser(isFavorite: Boolean, username: String) = githubRepository.updateFavoriteUser(isFavorite, username)
}