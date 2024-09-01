package com.mt1729.notes.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class TagRepository {
    private val _tags = MutableStateFlow<List<Tag>>(tmpTags)
    fun getTags(): Flow<List<Tag>> = _tags

    fun updateTag(tag: Tag) {}
    fun deleteTag(tag: Tag) {}
    fun addTag(tag: Tag) {
        val tags = _tags.value
        val tagExists = tags.find { it.name == tag.name } != null

        if (!tagExists) {
            _tags.update { it + tag }
        }
    }
}
