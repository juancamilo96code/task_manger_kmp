package com.vamaju.task.presentation.task_details.model

sealed class TaskDetailsIntent {
    data class LoadTaskDetails(val taskId: Long) : TaskDetailsIntent()
    data class DeleteTaskDetails(val taskId: Long) : TaskDetailsIntent()
    data class UpdateTask(val task: Task) : TaskDetailsIntent()
}