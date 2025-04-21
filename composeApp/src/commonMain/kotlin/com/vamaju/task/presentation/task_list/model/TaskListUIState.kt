package com.vamaju.task.presentation.task_list.model

import com.vamaju.task.presentation.task_details.model.Tag

data class TaskListUIState (
    val tasks: List<TaskItem> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val formDataValidated: TaskFormData? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)