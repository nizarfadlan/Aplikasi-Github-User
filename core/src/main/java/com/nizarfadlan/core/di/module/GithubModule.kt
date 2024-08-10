package com.nizarfadlan.core.di.module

import com.nizarfadlan.core.data.datasource.GithubDataSourceImpl
import com.nizarfadlan.core.data.repository.GithubRepositoryImpl
import com.nizarfadlan.core.domain.datasource.GithubDataSource
import com.nizarfadlan.core.domain.repository.GithubRepository
import org.koin.dsl.module

val githubDataSourceModule =
    module {
        single<GithubDataSource> { GithubDataSourceImpl(get(), get()) }
    }

val githubRepositoryModule =
    module {
        factory<GithubRepository> { GithubRepositoryImpl(get()) }
    }