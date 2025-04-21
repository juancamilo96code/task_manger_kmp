package com.vamaju.task.domain.task.usecases

import com.vamaju.task.domain.TaskRepository
import com.vamaju.task.domain.task.model.TaskModel
import com.vamaju.task.domain.task.model.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilterTask(val taskRepository: TaskRepository) {

    suspend operator fun invoke(
        filter: String
    ): Flow<Result<List<TaskModel>>> =
        taskRepository.filterTasks(filter).map {
            it.map { taskWithTagsCache ->
                taskWithTagsCache.map { taskWithTags ->
                    taskWithTags.mapToDomain()
                }
            }
        }
}