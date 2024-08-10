package com.nizarfadlan.core.data.datasource

import com.nizarfadlan.core.data.local.dao.UserDao
import com.nizarfadlan.core.data.local.room.UserDatabase
import com.nizarfadlan.core.data.remote.response.ItemsItem
import com.nizarfadlan.core.data.remote.response.SearchUserResponse
import com.nizarfadlan.core.data.remote.service.UserService
import com.nizarfadlan.core.domain.common.ResultState
import com.nizarfadlan.core.domain.datasource.GithubDataSource
import com.nizarfadlan.core.domain.model.UserEntity
import com.nizarfadlan.core.utils.toUserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GithubDataSourceImpl(
    private val service: UserService,
    database: UserDatabase
) : GithubDataSource {
    private val userDao: UserDao = database.getFavoriteUserDao()

    override fun getAllUsers(username: String): Flow<ResultState<SearchUserResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = service.searchUser(username)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override fun getDetailUser(username: String): Flow<ResultState<UserEntity>> = flow {
        emit(ResultState.Loading)
        try {
            val response = service.detailUser(username)
            if (userDao.isUserIsExist(username)) {
                userDao.updateDetailUser(
                    response.id,
                    username,
                    response.name ?: "",
                    response.followers ?: 0,
                    response.following ?: 0,
                    response.avatarUrl ?: "",
                    response.bio ?: "",
                    response.htmlUrl ?: "",
                    response.email ?: ""
                )
            } else {
                userDao.insertFavorite(response.toUserEntity(username))
            }
            val user = userDao.getUser(username)
            emit(ResultState.Success(user))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override fun getFollowers(username: String): Flow<ResultState<List<ItemsItem>>> = flow {
        emit(ResultState.Loading)
        try {
            val response = service.followersUser(username)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override fun getFollowing(username: String): Flow<ResultState<List<ItemsItem>>> = flow {
        emit(ResultState.Loading)
        try {
            val response = service.followingUser(username)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override fun getAllFavoriteUsers(): Flow<List<UserEntity>> = flow {
        emit(userDao.getAllFavoriteUsers())
    }

    override suspend fun updateFavoriteUser(isFavorite: Boolean, username: String) {
        userDao.updateFavorite(isFavorite, username)
    }
}