package com.nizarfadlan.core.di.module

import android.app.Application
import androidx.room.Room
import com.nizarfadlan.core.data.local.room.UserDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

fun provideDatabase(application: Application): UserDatabase {
    val passphrase: ByteArray = SQLiteDatabase.getBytes("githubUser".toCharArray())
    val factory = SupportFactory(passphrase)
    return Room.databaseBuilder(application, UserDatabase::class.java, "user-db")
        .fallbackToDestructiveMigration()
        .openHelperFactory(factory)
        .build()
}

fun provideFavoriteUserDao(database: UserDatabase) = database.getFavoriteUserDao()

val localModule =
    module {
        single { provideFavoriteUserDao(get()) }
        single { provideDatabase(androidApplication()) }
    }