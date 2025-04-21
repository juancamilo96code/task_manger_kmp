package com.vamaju.task.presentation.task_details.model

import com.vamaju.task.domain.task.model.TaskModel

data class Task(
    val id: Long,
    val title: String,
    val description: String?,
    val latitude: Double?,
    val longitude: Double?,
    val address: String?,
    val responsible: String?,
    val isCompleted: Boolean,
    val tags: List<Tag>,
)

fun TaskModel.mapToUI() = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    latitude = this.latitude,
    longitude = this.longitude,
    address = this.address,
    responsible = this.responsible,
    isCompleted = this.isCompleted,
    tags = this.tags.map { it.mapToUI() }
)

fun Task.mapToDomain() = TaskModel(
    id = this.id,
    title = this.title,
    description = this.description,
    latitude = this.latitude,
    longitude = this.longitude,
    address = this.address,
    responsible = this.responsible,
    isCompleted = this.isCompleted,
    tags = this.tags.map { it.mapToDomain() }
)