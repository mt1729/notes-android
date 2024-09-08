package com.mt1729.notes.model.repository

import com.mt1729.notes.model.Note
import com.mt1729.notes.model.Tag
import com.mt1729.notes.model.database.dao.NoteTagDao
import com.mt1729.notes.model.database.dao.TagDao
import com.mt1729.notes.model.database.entity.NoteTagEntity
import kotlinx.coroutines.flow.map

class TagRepository(
    private val tagDao: TagDao,
    private val noteTagDao: NoteTagDao,
) {
    val tags = tagDao.getAll().map { entities ->
        entities.map { Tag(from = it) }
    }

    suspend fun updateTag(tag: Tag) {
        tagDao.update(tag.toEntity())
    }

    suspend fun deleteTag(tag: Tag) {
        tagDao.delete(tag.toEntity())
    }

    suspend fun createTag(tag: Tag) {
        tagDao.insert(tag.toEntity())
    }

    suspend fun createTagForNote(tagToCreate: Tag, existingNote: Note) {
        val newTagId = tagDao.insert(tagToCreate.toEntity())

        // Note should already have been created (non 0 id)
        val existingNoteId = existingNote.id ?: return
        val newJoin = NoteTagEntity(existingNoteId, newTagId)
        noteTagDao.insertNoteTagJoins(newJoin)
    }

    suspend fun addTagToNote(existingTag: Tag, existingNote: Note) {
        val newJoin = NoteTagEntity(existingNote.toEntity().noteId, existingTag.toEntity().tagId)
        noteTagDao.insertNoteTagJoins(newJoin)
    }

    suspend fun removeTagFromNote(tagToRemove: Tag, existingNote: Note) {
        val oldJoin = NoteTagEntity(existingNote.toEntity().noteId, tagToRemove.toEntity().tagId)
        noteTagDao.deleteNoteTagJoins(oldJoin)
    }
}
