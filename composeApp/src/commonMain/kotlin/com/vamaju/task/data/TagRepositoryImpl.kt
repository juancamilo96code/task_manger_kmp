package com.vamaju.task.data

import com.vamaju.task.Tag
import com.vamaju.task.data.cache.LocalDatabase
import com.vamaju.task.domain.tag.TagRepository

class TagRepositoryImpl(private val database: LocalDatabase): TagRepository {

    override suspend fun getTagsByName(name: String): Result<List<Tag>> {
        return database.getTags(name)
    }
}