package com.vamaju.task.domain.tag.usecases

import com.vamaju.task.domain.tag.TagRepository
import com.vamaju.task.domain.tag.model.TagModel
import com.vamaju.task.domain.tag.model.mapToDomain

class GetTagsByName(val tagRepository: TagRepository) {

    suspend operator fun invoke(name: String): Result<List<TagModel>> {
        return tagRepository.getTagsByName(name).map { it.map { tag -> tag.mapToDomain() } }
    }
}