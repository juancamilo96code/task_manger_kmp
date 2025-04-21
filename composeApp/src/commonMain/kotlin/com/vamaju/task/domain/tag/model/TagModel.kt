package com.vamaju.task.domain.tag.model

import com.vamaju.task.Tag


data class TagModel(
    val id: Long,
    val name: String,
)

fun TagModel.mapToCache() = Tag(
    id = this.id,
    name = this.name
)
fun Tag.mapToDomain() = TagModel(
    id = this.id,
    name = this.name
)