package com.vamaju.task.di

import com.vamaju.task.data.cache.DatabaseDriverFactory
import com.vamaju.task.data.cache.DesktopDatabaseDriverFactory
import com.vamaju.task.presentation.commons.DesktopLocationProvider
import com.vamaju.task.presentation.commons.LocationProvider
import org.koin.dsl.module

actual val targetModule = module {
    single<DatabaseDriverFactory> {
        DesktopDatabaseDriverFactory()
    }

    single<LocationProvider> { DesktopLocationProvider() }
}