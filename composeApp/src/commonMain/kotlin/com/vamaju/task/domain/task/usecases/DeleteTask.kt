package com.vamaju.task.domain.task.usecases

import com.vamaju.task.domain.TaskRepository

class DeleteTask(val taskRepository: TaskRepository) {
    suspend operator fun invoke(id: Long): Result<Unit> {
        return taskRepository.deleteTask(id)
    }
}