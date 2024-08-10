package com.nizarfadlan.core.di.module

import com.nizarfadlan.core.data.pref.SettingPreferences
import org.koin.dsl.module

val preferenceModule =
    module {
        single { SettingPreferences(get()) }
    }