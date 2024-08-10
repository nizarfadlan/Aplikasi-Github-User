package com.nizarfadlan.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nizarfadlan.core.data.local.dao.UserDao
import com.nizarfadlan.core.domain.model.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getFavoriteUserDao(): UserDao
}
