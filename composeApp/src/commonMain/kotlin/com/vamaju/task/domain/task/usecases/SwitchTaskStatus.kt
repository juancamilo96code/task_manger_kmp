package com.vamaju.task.domain.task.usecases

import com.vamaju.task.domain.TaskRepository

class SwitchTaskStatus (val taskRepository: TaskRepository){
    suspend operator fun invoke(
        taskId: Long,
        isCompleted: Boolean
    ) = taskRepository.switchTaskStatus(taskId, isCompleted)
}