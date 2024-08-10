package com.nizarfadlan.aplikasigithubuser.app

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.nizarfadlan.aplikasigithubuser.di.viewModelModule
import com.nizarfadlan.core.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GithubUserApp : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()

        val appModules = listOf(
            *coreModule.toTypedArray(),
            viewModelModule,
        )

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@GithubUserApp)
            modules(appModules)
        }
    }
}