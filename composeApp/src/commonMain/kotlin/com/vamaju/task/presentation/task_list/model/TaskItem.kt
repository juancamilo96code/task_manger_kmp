package com.vamaju.task.presentation.task_list.model

import com.vamaju.task.domain.task.model.TaskModel
import com.vamaju.task.presentation.task_details.model.Tag
import com.vamaju.task.presentation.task_details.model.mapToUI

data class TaskItem(
    val id: Long,
    val title: String,
    val responsible: String?,
    var isCompleted: Boolean,
    val tags: List<Tag>,
)

fun TaskModel.mapToItemUI() = TaskItem(
    id = this.id,
    title = this.title,
    responsible = this.responsible,
    isCompleted = this.isCompleted,
    tags = this.tags.map { it.mapToUI() }
)