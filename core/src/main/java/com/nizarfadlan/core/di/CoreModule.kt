package com.nizarfadlan.core.di

import com.nizarfadlan.core.di.module.githubDataSourceModule
import com.nizarfadlan.core.di.module.githubRepositoryModule
import com.nizarfadlan.core.di.module.localModule
import com.nizarfadlan.core.di.module.preferenceModule
import com.nizarfadlan.core.di.module.remoteModule
import com.nizarfadlan.core.di.module.useCaseModule

val coreModule = listOf(
    localModule,
    preferenceModule,
    remoteModule,
    useCaseModule,
    githubDataSourceModule,
    githubRepositoryModule
)