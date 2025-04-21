package com.vamaju.task.presentation.task_list.model

import com.vamaju.task.presentation.task_details.model.Task

sealed class TaskListIntent {
    data class ValidateFormTask(val taskFormData: TaskFormData) : TaskListIntent()
    data class CreateTask(val task: Task) : TaskListIntent()
    data class UpdateStatusTask(val taskId: Long,val isCompleted: Boolean) : TaskListIntent()
    data class SearchTask(val query: String) : TaskListIntent()
    data class SearchTags(val query: String) : TaskListIntent()
    data object LoadAllTask : TaskListIntent()
}