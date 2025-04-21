package com.vamaju.task.di

import com.vamaju.task.data.TagRepositoryImpl
import com.vamaju.task.data.TaskRepositoryImpl
import com.vamaju.task.data.cache.LocalDatabase
import com.vamaju.task.domain.TaskRepository
import com.vamaju.task.domain.tag.TagRepository
import com.vamaju.task.domain.tag.usecases.GetTagsByName
import com.vamaju.task.domain.task.usecases.*
import com.vamaju.task.presentation.task_details.TaskDetailsViewModel
import com.vamaju.task.presentation.task_list.TaskListViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


expect val targetModule: Module

val sharedModule = module {
    single<LocalDatabase> { LocalDatabase(get()) }
    single<TaskRepository> { TaskRepositoryImpl(database = get()) }
    single<TagRepository> { TagRepositoryImpl(database = get()) }

    single<CreateTask> { CreateTask(taskRepository = get()) }
    single<DeleteTask> { DeleteTask(taskRepository = get()) }
    single<FilterTask> { FilterTask(taskRepository = get()) }
    single<GetAllTask> { GetAllTask(taskRepository = get()) }
    single<GetTaskById> { GetTaskById(taskRepository = get()) }
    single<SwitchTaskStatus> { SwitchTaskStatus(taskRepository = get()) }
    single<UpdateTask> { UpdateTask(taskRepository = get()) }
    single<GetTagsByName> { GetTagsByName(tagRepository = get()) }

    viewModel {
        TaskListViewModel(
            createTask = get(),
            getAllTask = get(),
            filterTask = get(),
            switchTaskStatus = get(),
            getTagsByName = get()
        )
    }
    viewModel {
        TaskDetailsViewModel(
            getTaskById = get(),
            deleteTask = get(),
            updateTask = get(),
        )
    }
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}
