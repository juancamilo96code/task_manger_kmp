package com.vamaju.task.domain.task.model

import com.vamaju.task.data.cache.model.TaskWithTagsCache
import com.vamaju.task.domain.tag.model.TagModel
import com.vamaju.task.domain.tag.model.mapToCache
import com.vamaju.task.domain.tag.model.mapToDomain

data class TaskModel(
    val id: Long,
    val title: String,
    val description: String?,
    val latitude: Double?,
    val longitude: Double?,
    val address: String?,
    val responsible: String?,
    val isCompleted: Boolean,
    val tags: List<TagModel>,
)

fun TaskModel.mapToCache() = TaskWithTagsCache(
    id = this.id,
    title = this.title,
    description= this.description,
    latitude = this.latitude,
    longitude = this.longitude,
    address = this.address,
    responsible = this.responsible,
    isCompleted = this.isCompleted,
    tags = this.tags.map { it.mapToCache() }
)

fun TaskWithTagsCache.mapToDomain() = TaskModel(
    id = this.id,
    title = this.title,
    description= this.description,
    latitude = this.latitude,
    longitude = this.longitude,
    address = this.address,
    responsible = this.responsible,
    isCompleted = this.isCompleted,
    tags = this.tags.map { it.mapToDomain() }
)