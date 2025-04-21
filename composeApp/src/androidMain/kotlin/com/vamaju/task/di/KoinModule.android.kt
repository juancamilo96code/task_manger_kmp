package com.vamaju.task.di

import com.vamaju.task.data.cache.DatabaseDriverFactory
import com.vamaju.task.data.cache.AndroidDatabaseDriverFactory
import com.vamaju.task.presentation.commons.AndroidLocationProvider
import com.vamaju.task.presentation.commons.LocationProvider
import com.vamaju.task.presentation.commons.appContext
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val targetModule = module {
    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(androidContext())
    }

    single<LocationProvider> { AndroidLocationProvider(appContext) }
}