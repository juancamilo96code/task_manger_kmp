package com.vamaju.task.presentation.commons

import android.annotation.SuppressLint
import android.content.Context
import com.vamaju.task.R

@SuppressLint("StaticFieldLeak")
lateinit var androidContext: Context

fun initStringResources(context: Context) {
    androidContext = context
}

actual fun stringAppName(): String = androidContext.getString(R.string.app_name)
actual fun stringTaskInProgress(): String = androidContext.getString(R.string.task_in_progress)
actual fun stringTaskFinished(): String = androidContext.getString(R.string.task_finished)
actual fun stringSearchTask(): String = androidContext.getString(R.string.search_task)
actual fun stringTitleTask(): String = androidContext.getString(R.string.task_name)
actual fun stringDescriptionTask(): String = androidContext.getString(R.string.description)
actual fun stringResponsibleTask(): String = androidContext.getString(R.string.responsible)
actual fun stringTag(): String = androidContext.getString(R.string.tag)
actual fun stringTags(): String = androidContext.getString(R.string.tags)
actual fun stringSave(): String = androidContext.getString(R.string.save)
actual fun stringCancel(): String = androidContext.getString(R.string.cancel)
actual fun stringUpdate(): String = androidContext.getString(R.string.update)
actual fun stringDelete(): String = androidContext.getString(R.string.delete)
actual fun stringEdit(): String = androidContext.getString(R.string.edit)
actual fun stringSearchTag(): String = androidContext.getString(R.string.search_tag)