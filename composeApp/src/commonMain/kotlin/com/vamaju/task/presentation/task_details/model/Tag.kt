package com.vamaju.task.presentation.task_details.model

import com.vamaju.task.domain.tag.model.TagModel

data class Tag(
    val id: Long,
    val name: String,
)

fun TagModel.mapToUI() = Tag(
    id = this.id,
    name = this.name
)

fun Tag.mapToDomain() = TagModel(
    id = this.id,
    name = this.name
)