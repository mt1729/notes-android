package com.mt1729.notes.model

import com.mt1729.notes.model.database.TagDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TagRepository(
    private val tagDao: TagDao,
) {
    private val _tags = tagDao.getAll().map { entities ->
        entities.map { Tag(from = it) }
    }
    fun getTags(): Flow<List<Tag>> = _tags

    suspend fun updateTag(tag: Tag) {
        tagDao.update(tag.toEntity())
    }
    suspend fun deleteTag(tag: Tag) {
        tagDao.delete(tag.toEntity())
    }

    suspend fun addTag(tag: Tag) {
        tagDao.insert(tag.toEntity())
    }
}
