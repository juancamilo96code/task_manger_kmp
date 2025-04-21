package com.vamaju.task

import android.app.Application
import com.vamaju.task.di.initializeKoin
import com.vamaju.task.presentation.commons.appContext
import com.vamaju.task.presentation.commons.initStringResources
import org.koin.android.ext.koin.androidContext

class TaskManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        initStringResources(this)
        initializeKoin(
            config = { androidContext(this@TaskManagerApplication) }
        )
    }
}