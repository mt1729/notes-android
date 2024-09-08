package com.mt1729.notes.model.repository

import com.mt1729.notes.model.Note
import com.mt1729.notes.model.Tag
import com.mt1729.notes.model.database.NoteDao
import com.mt1729.notes.model.database.NoteTagJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository(
    private val noteDao: NoteDao,
) {
    private val _notes = noteDao.getNotesWithTags().map { relations ->
        // Prepare entities for domain
        relations.map { relation ->
            Note(
                from = relation.note,
                tags = relation.tags.map { Tag(from = it) }
            )
        }
    }
    fun getNotes(): Flow<List<Note>> = _notes

    suspend fun updateNote(note: Note) {
        noteDao.updateNotes(note.toEntity())
    }

    suspend fun addTagToNote(note: Note, tag: Tag) {
        val newJoin = NoteTagJoin(note.toEntity().noteId, tag.toEntity().tagId)
        noteDao.insertNoteTagJoins(newJoin)
    }

    suspend fun removeTagFromNote(note: Note, tag: Tag) {
        val oldJoin = NoteTagJoin(note.toEntity().noteId, tag.toEntity().tagId)
        noteDao.deleteNoteTagJoins(oldJoin)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNotes(note.toEntity())
    }
    suspend fun addNote(note: Note) {
        noteDao.insertNotes(note.toEntity())
    }
}
