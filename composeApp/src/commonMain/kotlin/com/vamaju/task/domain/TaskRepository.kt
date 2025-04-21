package com.vamaju.task.domain

import com.vamaju.task.Task
import com.vamaju.task.data.cache.model.TaskWithTagsCache
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun getAllTasks(): Flow<Result<List<TaskWithTagsCache>>>

    suspend fun createTask(taskWithTagsCache: TaskWithTagsCache): Result<Unit>

    suspend fun updateTask(taskWithTagsCache: TaskWithTagsCache): Result<Unit>

    suspend fun deleteTask(taskId: Long): Result<Unit>

    suspend fun switchTaskStatus(
        taskId: Long,
        isCompleted: Boolean
    ): Result<Unit>

    suspend fun getTaskById(taskId: Long): Result<TaskWithTagsCache?>

    suspend fun filterTasks(
        search: String,
    ): Flow<Result<List<TaskWithTagsCache>>>

}