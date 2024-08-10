package com.nizarfadlan.core.di.module

import com.nizarfadlan.core.domain.usecase.GithubInteractor
import com.nizarfadlan.core.domain.usecase.GithubUseCase
import org.koin.dsl.module

val useCaseModule =
    module {
        factory<GithubUseCase> { GithubInteractor(get()) }
    }