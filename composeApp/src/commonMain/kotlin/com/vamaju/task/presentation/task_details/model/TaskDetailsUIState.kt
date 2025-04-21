package com.vamaju.task.presentation.task_details.model


data class TaskDetailsUIState (
    val task: Task? = null,
    val tags: List<Tag> = emptyList(),
    var isTaskDeleted : Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)