package com.vamaju.task.domain.tag

import com.vamaju.task.Tag

interface TagRepository {

    suspend fun getTagsByName(name: String): Result<List<Tag>>
}