package com.vamaju.task.data.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.vamaju.task.Tag
import com.vamaju.task.TaskDatabase
import com.vamaju.task.data.cache.model.TaskWithTagsCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlin.coroutines.coroutineContext


interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

class LocalDatabase(
    dataBaseDriverFactory: DatabaseDriverFactory
) {
    private val dataBase = TaskDatabase(
        dataBaseDriverFactory.createDriver()
    )

    private val query = dataBase.taskDatabaseQueries

    suspend fun observeAllTasksWithTags(): Flow<Result<List<TaskWithTagsCache>>> {
        return query.selectAllTasks()
            .asFlow()
            .mapToList(coroutineContext)
            .map { list ->
                try {
                    val tasks = list.groupBy { it.taskId }.map { (_, group) ->
                        val first = group.first()
                        TaskWithTagsCache(
                            id = first.taskId,
                            title = first.title,
                            description = first.description,
                            latitude = first.latitude,
                            longitude = first.longitude,
                            address = first.address,
                            responsible = first.responsible,
                            isCompleted = first.isCompleted == 1L,
                            tags = group.filter { it.tagId != null }
                                .map { Tag(it.tagId!!, it.tagName!!) }
                                .distinctBy { it.id }
                        )
                    }
                    Result.success(tasks)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
    }

    suspend fun createTask(taskWithTagsCache: TaskWithTagsCache): Result<Unit> {
        return try {
            query.insertTask(
                title = taskWithTagsCache.title,
                description = taskWithTagsCache.description,
                latitude = taskWithTagsCache.latitude,
                longitude = taskWithTagsCache.longitude,
                address = taskWithTagsCache.address,
                isCompleted = if (taskWithTagsCache.isCompleted) 1 else 0, // ojo: 1 = true
                responsible = taskWithTagsCache.responsible,
            )

            val taskId = query.getLastInsertId().executeAsOne()
            updateTaskTag(taskId, taskWithTagsCache.tags)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateTask(taskWithTagsCache: TaskWithTagsCache): Result<Unit> {
        return try {
            query.updateTask(
                title = taskWithTagsCache.title,
                description = taskWithTagsCache.description,
                latitude = taskWithTagsCache.latitude,
                longitude = taskWithTagsCache.longitude,
                address = taskWithTagsCache.address,
                isCompleted = if (taskWithTagsCache.isCompleted) 1 else 0,
                responsible = taskWithTagsCache.responsible,
                id = taskWithTagsCache.id
            )

            updateTaskTag(taskWithTagsCache.id, taskWithTagsCache.tags)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun updateTaskTag(
        taskId: Long,
        tags: List<Tag>
    ) {
        query.deleteTagsForTask(taskId)
        tags.forEach { tag ->
            var localTag: Tag? = query.selectTagByName(tag.name).executeAsOneOrNull()
            if (localTag == null) {
                query.insertTag(tag.name)
                localTag = query.selectTagByName(tag.name).executeAsOneOrNull()
            }
            localTag?.id?.let {
                query.insertTaskTag(
                    taskId = taskId,
                    tagId = it
                )
            }
        }
    }

    suspend fun switchTaskStatus(
        taskId: Long,
        taskStatus: Boolean
    ): Result<Unit> {
        return try {
            query.switchTask(
                isCompleted = if (taskStatus) 1 else 0,
                id = taskId
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTask(
        taskId: Long
    ): Result<Unit> {
        return try {
            query.deleteTask(id = taskId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getTaskById(id: Long): Result<TaskWithTagsCache?> {
        return try {
            val rows = query.getTaskById(id).executeAsList()
            val task = rows.groupBy { it.taskId }
                .map { (_, group) ->
                    val first = group.first()
                    TaskWithTagsCache(
                        id = first.taskId,
                        title = first.title,
                        description = first.description,
                        latitude = first.latitude,
                        longitude = first.longitude,
                        address = first.address,
                        responsible = first.responsible,
                        isCompleted = first.isCompleted == 1.toLong(),
                        tags = group.filter { it.tagId != null }
                            .map { Tag(it.tagId!!, it.tagName!!) }
                            .distinctBy { it.id }
                    )
                }.firstOrNull()

            Result.success(task)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun observeFilterTasksWithTags(filter: String): Flow<Result<List<TaskWithTagsCache>>> {
        return try {
            query.searchTasks(filter)
                .asFlow()
                .mapToList(coroutineContext)
                .map { list ->
                    Result.success(
                        list.groupBy { it.taskId }.map { (_, group) ->
                            val first = group.first()
                            TaskWithTagsCache(
                                id = first.taskId,
                                title = first.title,
                                description = first.description,
                                latitude = first.latitude,
                                longitude = first.longitude,
                                address = first.address,
                                responsible = first.responsible,
                                isCompleted = first.isCompleted == 1.toLong(),
                                tags = group.filter { it.tagId != null }
                                    .map { Tag(it.tagId!!, it.tagName!!) }
                                    .distinctBy { it.id }
                            )
                        }
                    )
                }
        } catch (e: Exception) {
            flowOf(Result.failure(e))
        }
    }

    fun getTags(name: String): Result<List<Tag>> {
        return try {
            val tags = query.selectAllTags().executeAsList()
            Result.success(tags)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}