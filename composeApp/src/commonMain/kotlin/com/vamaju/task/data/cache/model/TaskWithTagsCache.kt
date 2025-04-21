package com.vamaju.task.data.cache.model

import com.vamaju.task.Tag

data class TaskWithTagsCache(
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