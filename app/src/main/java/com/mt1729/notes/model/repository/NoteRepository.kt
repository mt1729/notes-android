package com.mt1729.notes.model.repository

import com.mt1729.notes.model.Note
import com.mt1729.notes.model.Tag
import com.mt1729.notes.model.database.dao.NoteDao
import kotlinx.coroutines.flow.map

class NoteRepository(
    private val noteDao: NoteDao,
) {
    val notes = noteDao.getNotesWithTags().map { relations ->
        // Prepare entities for domain
        relations.map { relation ->
            Note(
                from = relation.note,
                tags = relation.tags.map { Tag(from = it) }
            )
        }
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNotes(note.toEntity())
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNotes(note.toEntity())
    }

    suspend fun addNote(note: Note) {
        noteDao.insertNotes(note.toEntity())
    }
}
