package com.nizarfadlan.core.utils

import com.nizarfadlan.core.data.remote.response.DetailUserResponse
import com.nizarfadlan.core.domain.model.UserEntity

fun DetailUserResponse.toUserEntity(username: String): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        followers = followers,
        following = following,
        name = name,
        bio = bio,
        email = email
    )
}