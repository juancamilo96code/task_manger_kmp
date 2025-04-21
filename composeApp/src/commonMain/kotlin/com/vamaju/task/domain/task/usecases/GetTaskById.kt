package com.vamaju.task.domain.task.usecases

import com.vamaju.task.domain.TaskRepository
import com.vamaju.task.domain.task.model.TaskModel
import com.vamaju.task.domain.task.model.mapToDomain

class GetTaskById(val taskRepository: TaskRepository) {
    suspend operator fun invoke(
        id: Long
    ): Result<TaskModel?> =
        taskRepository.getTaskById(id).map { it?.mapToDomain() }
}