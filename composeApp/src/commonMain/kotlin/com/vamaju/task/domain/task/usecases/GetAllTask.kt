package com.vamaju.task.domain.task.usecases

import com.vamaju.task.domain.TaskRepository
import com.vamaju.task.domain.task.model.TaskModel
import com.vamaju.task.domain.task.model.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllTask(val taskRepository: TaskRepository) {

    suspend operator fun invoke(): Flow<Result<List<TaskModel>>> =
        taskRepository.getAllTasks().map {
            it.map { taskWithTagsCache ->
                taskWithTagsCache.map { taskWithTags ->
                    taskWithTags.mapToDomain()
                }
            }
        }
}