package com.vamaju.task.data

import com.vamaju.task.data.cache.LocalDatabase
import com.vamaju.task.data.cache.model.TaskWithTagsCache
import com.vamaju.task.domain.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val database: LocalDatabase) : TaskRepository {

    override suspend fun getAllTasks(): Flow<Result<List<TaskWithTagsCache>>> {
        return database.observeAllTasksWithTags()
    }

    override suspend fun createTask(taskWithTagsCache: TaskWithTagsCache): Result<Unit> {
        return database.createTask(taskWithTagsCache)
    }

    override suspend fun updateTask(taskWithTagsCache: TaskWithTagsCache): Result<Unit> {
        return database.updateTask(taskWithTagsCache)
    }

    override suspend fun deleteTask(taskId: Long): Result<Unit> {
        return database.deleteTask(taskId)
    }

    override suspend fun switchTaskStatus(taskId: Long, isCompleted: Boolean) : Result<Unit>{
        return database.switchTaskStatus(taskId, isCompleted)
    }

    override suspend fun getTaskById(taskId: Long): Result<TaskWithTagsCache?> {
        return database.getTaskById(taskId)
    }

    override suspend fun filterTasks(search: String): Flow<Result<List<TaskWithTagsCache>>> {
        return database.observeFilterTasksWithTags(search)
    }

}