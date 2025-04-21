package com.vamaju.task.domain.task.usecases

import com.vamaju.task.domain.TaskRepository
import com.vamaju.task.domain.task.model.TaskModel
import com.vamaju.task.domain.task.model.mapToCache

class UpdateTask (val taskRepository: TaskRepository){
    suspend operator fun invoke(task: TaskModel): Result<Unit> {
        return taskRepository.updateTask(task.mapToCache())
    }
}